(ns association.facts
  "Central-bank rule catalog for the Bank of Japan (日本銀行 / BOJ) -- a
  13th industry-association-level source (see cloud-itonami-assoc-6419-jpn-zenginkyo,
  -6512-jpn-sonpo, -6612-jpn-jsda, -6419-deu-bankenverband, -6612-usa-finra,
  -6512-usa-naic, -6920-jpn-jicpa, -6920-usa-aicpa, -6419-fra-fbf,
  -6511-jpn-seiho, -6910-jpn-nichibenren, -6810-jpn-recaj for the first
  twelve) per ADR-2607141700 (cloud-itonami-compliance-fact-federation).
  The FIRST entry aligned to ISIC 6411 (central banking) -- a new
  industry code for this family. Every entry cites an OFFICIAL boj.or.jp
  URL -- never fabricated. A rule not in this table has NO spec-basis,
  full stop; extend `catalog`, do not invent an id/url.

  Both entries were verified by direct WebFetch render (no PDF fallback
  needed this time): 日本銀行業務方法書 (Business Operations Manual)
  confirmed initially enacted 1998-04-01, most recently amended
  2026-04-01; 日本銀行の「独立性」と「透明性」――新日本銀行法の概要
  (Independence and Transparency -- Overview of the New Bank of Japan
  Law) confirmed as the official explanatory page for the governance
  framework established by the 1997 revision of the Bank of Japan Act
  (enacted 1997-06-18, effective 1998-04-01).")

(def catalog
  "assoc-slug -> vector of self-regulatory/governance rule entries."
  {"boj"
   [{:association-rule/id "boj.gyomu-hoho-sho"
     :association-rule/title "日本銀行業務方法書 (Bank of Japan Business Operations Manual)"
     :association-rule/association "boj"
     :association-rule/isic "6411"
     :association-rule/country "JPN"
     :association-rule/kind :self-regulatory-code
     :association-rule/url "https://www.boj.or.jp/about/boj_law/ghousyo.htm"
     :association-rule/url-provenance :official-association-site
     :association-rule/established-date "1998-04-01"
     :association-rule/last-revised-date "2026-04-01"
     :association-rule/retrieved-at "2026-07-15"
     :association-rule/topic #{:governance :operations}}
    {:association-rule/id "boj.independence-transparency-overview"
     :association-rule/title "日本銀行の「独立性」と「透明性」――新日本銀行法の概要 (Independence and Transparency -- Overview of the New Bank of Japan Law)"
     :association-rule/association "boj"
     :association-rule/isic "6411"
     :association-rule/country "JPN"
     :association-rule/kind :governance-program
     :association-rule/url "https://www.boj.or.jp/about/outline/expdokuritsu.htm"
     :association-rule/url-provenance :official-association-site
     :association-rule/established-date "1998-04-01"
     :association-rule/retrieved-at "2026-07-15"
     :association-rule/topic #{:governance :transparency}}]})

(defn spec-basis [assoc-slug] (get catalog assoc-slug))

(defn coverage
  ([] (coverage (keys catalog)))
  ([slugs]
   (let [have (filter catalog slugs)
         missing (remove catalog slugs)]
     {:requested (count slugs)
      :covered (count have)
      :covered-associations (vec (sort have))
      :missing-associations (vec (sort missing))
      :note (str "cloud-itonami-assoc-6411-jpn-boj Wave 0 (ADR-2607141700): "
                 (count (get catalog "boj")) " boj entries seeded with an "
                 "official boj.or.jp citation. Extend "
                 "`association.facts/catalog`, never fabricate a rule id/url.")})))

(defn by-topic [assoc-slug topic]
  (filterv #(contains? (:association-rule/topic %) topic) (spec-basis assoc-slug)))
