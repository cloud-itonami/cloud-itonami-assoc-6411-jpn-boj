# cloud-itonami-assoc-6411-jpn-boj

Central-bank rule catalog for the **Bank of Japan** (日本銀行 / BOJ) — a
13th industry-association-level source, and the FIRST aligned to ISIC
6411 (central banking), alongside
[`cloud-itonami-assoc-6419-jpn-zenginkyo`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-jpn-zenginkyo),
[`cloud-itonami-assoc-6512-jpn-sonpo`](https://github.com/cloud-itonami/cloud-itonami-assoc-6512-jpn-sonpo),
[`cloud-itonami-assoc-6612-jpn-jsda`](https://github.com/cloud-itonami/cloud-itonami-assoc-6612-jpn-jsda),
[`cloud-itonami-assoc-6419-deu-bankenverband`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-deu-bankenverband),
[`cloud-itonami-assoc-6612-usa-finra`](https://github.com/cloud-itonami/cloud-itonami-assoc-6612-usa-finra),
[`cloud-itonami-assoc-6512-usa-naic`](https://github.com/cloud-itonami/cloud-itonami-assoc-6512-usa-naic),
[`cloud-itonami-assoc-6920-jpn-jicpa`](https://github.com/cloud-itonami/cloud-itonami-assoc-6920-jpn-jicpa),
[`cloud-itonami-assoc-6920-usa-aicpa`](https://github.com/cloud-itonami/cloud-itonami-assoc-6920-usa-aicpa),
[`cloud-itonami-assoc-6419-fra-fbf`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-fra-fbf),
[`cloud-itonami-assoc-6511-jpn-seiho`](https://github.com/cloud-itonami/cloud-itonami-assoc-6511-jpn-seiho),
[`cloud-itonami-assoc-6910-jpn-nichibenren`](https://github.com/cloud-itonami/cloud-itonami-assoc-6910-jpn-nichibenren),
and
[`cloud-itonami-assoc-6810-jpn-recaj`](https://github.com/cloud-itonami/cloud-itonami-assoc-6810-jpn-recaj).
Part of the [`cloud-itonami`](https://github.com/cloud-itonami)
compliance-fact family (ADR-2607141700,
`cloud-itonami-compliance-fact-federation`, in `com-junkawasaki/root`).

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on the Bank of Japan's
behalf.

Coverage is reported honestly through the bounded `association-facts` ABI: an
association not admitted by `association-covered?` has **no spec-basis**, full
stop — never fabricate one.

## Data

- `src/association_facts.kotoba` — the sole production catalog source.
- `schema/association-rule.edn` — DataScript schema.
- `data/datascript-tx.edn` — derived DataScript tx-data (query this
  alongside other `cloud-itonami`/`etzhayyim` compliance-fact sources via
  `com-junkawasaki/root`'s `scripts/compliance-fact-query.cljs`).

Both entries were verified by direct WebFetch render (rendered cleanly,
no PDF-download fallback needed): 日本銀行業務方法書 (Business
Operations Manual, initially enacted 1998-04-01, most recently amended
2026-04-01) and 日本銀行の「独立性」と「透明性」――新日本銀行法の概要
(Independence and Transparency — Overview of the New Bank of Japan Law).

## License

AGPL-3.0-or-later (matches the `cloud-itonami-iso3166-*` /
`-municipality-*` / `-assoc-*` / `-lei-*` convention). Rule text itself
remains the Bank of Japan's; this repo stores only citation metadata
(id/title/url/dates), not full rule text.
