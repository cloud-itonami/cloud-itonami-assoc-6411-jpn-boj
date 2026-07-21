# ADR 0001: Kotoba is the BOJ catalog source authority

- Status: Accepted
- Date: 2026-07-21

`src/association_facts.kotoba` is the sole production source. It preserves both
official citations, complete date precision, the absent second revision date,
and ordered topics. Unknown values and indexes fail closed; no effects are
declared. Conformance is semantic across reference, restricted JavaScript, and
instantiated typed WebAssembly; byte-identical compiler output is not required.
Clojure and the JVM are compiler/test hosts only.
