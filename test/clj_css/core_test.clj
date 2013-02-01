(ns clj-css.core-test
  (:use clojure.test
        clj-css.core))

(deftest test-parse-css
  (are [in out] (= (parse-css in) out)
    "" []
    "h1 { color: #abcdef; }" [:h1 {:color "#abcdef"}]
    ; "h1 span { color: #abcdef; }" [:h1 :span {:color "#abcdef"}]
    ))

(deftest test-emit-css
  (are [in out] (= (emit-css in) out)
    [] ""
    [:h1 {:color "#abcdef"}] "h1 { color: #abcdef; }"
    [:h1 :span {:color "#abcdef"}] "h1 span { color: #abcdef; }"))

(deftest test-parse-scss
  (are [in out] (= (parse-scss in) out)
    "" []))

(deftest test-scss-to-css
  (are [in out] (= (scss-to-css in) out)
    [] []))

(deftest test-css-to-scss
  (are [in out] (= (css-to-scss in) out)
    [] []))

; (run-all-tests)
