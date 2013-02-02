(ns clj-css.core-test
  (:use clojure.test
        clj-css.core))

(deftest #^{:focus "focus"} test-parse-css
  (are [in out] (= (parse-css in) out)
    "" []
    "h1 { color: white; }" [:h1 {:color "white"}]
    "h1 span { color: white; }" [:h1 :span {:color "white"}]
    ))

(deftest test-emit-css
  (are [in out] (= (emit-css in) out)
    [] ""
    [:h1 {:color "white"}] "h1 { color: white; }"
    [:h1 :span {:color "white"}] "h1 span { color: white; }"))

(deftest test-parse-scss
  (are [in out] (= (parse-scss in) out)
    "" []))

(deftest test-scss-to-css
  (are [in out] (= (scss-to-css in) out)
    [] []))

(deftest test-css-to-scss
  (are [in out] (= (css-to-scss in) out)
    [] []))
