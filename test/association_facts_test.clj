(ns association-facts-test
  (:require [clojure.java.io :as io] [clojure.java.shell :as shell]
            [clojure.test :refer [deftest is testing]]
            [kotoba.compiler.core :as compiler] [kotoba.compiler.ir :as ir]))
(def source (slurp "src/association_facts.kotoba"))
(defn call [kir f & xs] (ir/execute kir f (vec xs)))
(defn present [x] (when (second x) (nth x 2)))
(def fields ["id" "title" "association" "isic" "country" "kind" "url" "url-provenance"
             "established-date" "last-revised-date" "retrieved-at"])
(def expected
  [{"id" "boj.gyomu-hoho-sho" "title" "日本銀行業務方法書 (Bank of Japan Business Operations Manual)"
    "association" "boj" "isic" "6411" "country" "JPN" "kind" "self-regulatory-code"
    "url" "https://www.boj.or.jp/about/boj_law/ghousyo.htm" "url-provenance" "official-association-site"
    "established-date" "1998-04-01" "last-revised-date" "2026-04-01" "retrieved-at" "2026-07-15"}
   {"id" "boj.independence-transparency-overview"
    "title" "日本銀行の「独立性」と「透明性」――新日本銀行法の概要 (Independence and Transparency -- Overview of the New Bank of Japan Law)"
    "association" "boj" "isic" "6411" "country" "JPN" "kind" "governance-program"
    "url" "https://www.boj.or.jp/about/outline/expdokuritsu.htm" "url-provenance" "official-association-site"
    "established-date" "1998-04-01" "last-revised-date" nil "retrieved-at" "2026-07-15"}])
(deftest reference-preserves-authority
  (let [kir (:kir (compiler/compile-source source :js-kotoba-v1))
        observed (mapv (fn [i] (into {} (map (fn [f] [f (present (call kir 'entry-field "boj" i f))]) fields))) [0 1])]
    (is (= expected observed))
    (is (= [["governance" "operations"] ["governance" "transparency"]]
           (mapv (fn [i] (mapv #(present (call kir 'topic "boj" i %)) [0 1])) [0 1])))
    (is (= ["boj.gyomu-hoho-sho" "boj.independence-transparency-overview"]
           (mapv #(present (call kir 'by-topic-id "boj" "governance" %)) [0 1])))
    (is (= #{} (set (:effects kir))))
    (testing "fail closed"
      (is (zero? (call kir 'entry-count "bank-of-japan")))
      (is (nil? (present (call kir 'entry-field "boj" 2 "id"))))
      (is (nil? (present (call kir 'entry-field "boj" 1 "last-revised-date"))))
      (is (nil? (present (call kir 'topic "boj" 0 2))))
      (is (zero? (call kir 'by-topic-count "boj" "monetary-policy")))
      (is (nil? (present (call kir 'by-topic-id "boj" "operations" 1)))))))
(defn compiler-root [] (nth (iterate #(.getParent ^java.nio.file.Path %)
  (java.nio.file.Path/of (.toURI (io/resource "kotoba/compiler/core.clj")))) 4))
(defn base64 [x] (.encodeToString (java.util.Base64/getEncoder) x))
(deftest restricted-js-and-wasm-conform-semantically
  (let [js (compiler/compile-source source :js-kotoba-v1) wasm (compiler/compile-source source :wasm32-browser-kotoba-v1)
        js64 (base64 (.getBytes ^String (:source js) "UTF-8")) wasm64 (base64 ^bytes (:bytes wasm))
        p (shell/sh "node" "--input-type=module" "-e"
            (str "import(process.argv[1]).then(async h=>{const j=await import('data:text/javascript;base64," js64 "');const w=await h.instantiateKotoba(Buffer.from(process.argv[2],'base64'));const r=x=>{if(x['entry-field']('boj',0n,'last-revised-date')[2]!=='2026-04-01'||x['entry-field']('boj',1n,'last-revised-date')[1]!==false)throw Error('dates');if(x['by-topic-count']('boj','governance')!==2n||x['by-topic-id']('boj','transparency',0n)[2]!=='boj.independence-transparency-overview'||x['entry-count']('bank-of-japan')!==0n)throw Error('authority');};r(j.instantiateKotoba({}));r(w.instance.exports)}).catch(e=>{console.error(e);process.exit(99)})")
            (.toString (.toUri (.resolve (compiler-root) "runtime/browser-host.mjs"))) wasm64)]
    (is (zero? (:exit p)) (str (:out p) (:err p)))))
(deftest production-source-authority
  (is (= ["src/association_facts.kotoba"] (->> (file-seq (io/file "src")) (filter #(.isFile %)) (map str) sort vec))))
