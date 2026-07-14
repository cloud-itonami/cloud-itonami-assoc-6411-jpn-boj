(ns association.facts-test
  (:require [clojure.test :refer [deftest is]]
            [association.facts :as facts]))

(deftest boj-has-spec-basis
  (let [sb (facts/spec-basis "boj")]
    (is (= 2 (count sb)))
    (is (every? #(= "6411" (:association-rule/isic %)) sb))
    (is (every? #(= "JPN" (:association-rule/country %)) sb))))

(deftest unknown-association-has-no-spec-basis
  (is (nil? (facts/spec-basis "keidanren")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["boj" "keidanren"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["keidanren"] (:missing-associations c)))))

(deftest by-topic-filters
  (is (= ["boj.independence-transparency-overview"]
         (mapv :association-rule/id (facts/by-topic "boj" :transparency))))
  (is (empty? (facts/by-topic "boj" :labor)))
  (is (empty? (facts/by-topic "keidanren" :governance))))
