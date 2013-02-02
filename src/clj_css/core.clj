(ns clj-css.core
  (:use [clojure.reflect]
        [clojure.pprint])
  (:require clojure.string)
  (:import com.steadystate.css.parser.CSSOMParser
           (org.w3c.dom.css CSSStyleSheet CSSRuleList CSSRule CSSStyleRule CSSStyleDeclaration)
           org.w3c.css.sac.InputSource
           java.io.StringReader))

; http://www.reddit.com/r/Clojure/comments/10svup/parsing_css/

(defn property-as-hash-map [property]
 (let [split-property (-> property .toString (clojure.string/split #": "))]
   {(-> split-property
        (get 0)
        keyword)
    (-> split-property
        (get 1))}))

; (let [k (keyword (-> (first (.getSelectors (.getSelectors rule)))
;                      .toString
;                      (clojure.string/replace (str "*" root-class " *.") "")))
(defn rule-as-hash-map [rule root-class]
  (let [k  (-> rule
               .getSelectors
               .getSelectors
               (->> (clojure.string/join " "))
               (clojure.string/split #"\s+")
               (->> (map #(keyword %))))
        v (reduce merge
            (map property-as-hash-map (.getProperties (.getStyle rule))))]
    (conj (vec k) v)))

(defn other-parse-css
 "Parse a CSS String, return map of rules"
 [css root-class]
 (let [stream (StringReader. css)
       source (InputSource. stream)
       parser (CSSOMParser.)
       stylesheet (.parseStyleSheet parser source nil nil)
       rule-list (.getRules (.getCssRules stylesheet))]
   (reduce merge
     (map rule-as-hash-map rule-list (repeat ".syntax")))))

; https://github.com/programble/csslj/blob/master/src/csslj/core.clj

(defn- render-ele [ele]
  (->> ele
    (map name)
    (interpose " ")
    (apply str)))

(defn- render-attrs [attrs]
  (apply str
    (sort
      (for [[attr value] attrs]
        (str (name attr) ": " value "; ")))))

(defn css
  "Takes any amount of vectors of CSS elements and renders them as CSS"
  ([vec]
     (let [ele (render-ele (drop-last vec))
           attrs (render-attrs (last vec))]
       (str ele " { " attrs "}")))
  ([vec & more]
     (->> (map css (cons vec more))
       (interpose " ")
       (apply str))))

; [:page
;   [:header-bar]
;   [:project-title]
;   [:project-toolbar]
;   [:wiki-page
;     [:code-example {:data-language "clojure"}
;       code....
;     ]
;   ]
; ]
; (defcomponent header-bar [user]
;   [
;     [:home-link]
;     [:notification-light]
;     [:input { :name "search" }]
;   ]
; )
; (defpallate-component header-bar
; [][][][]][
; )
; define htmlrec protocol
; define component protocol
; covert html to htmlrec
; convert htmlrec into html
; convert component into htmlrec

; (defn style-tree [style])
; (defn merge-styles [styles])
; (defn compact-styles [style])
; (defn css-string [style])
; 
; (defprotocol Style
;   (as-cljcss [this]))
; 
; (extend-protocol Style
;   Object
;   ([ :css ])
; )
; 
; (defrecord CljStyle [value])
; (extend-protocol Style
;   (as-scss))
; (defrecord SassStyle [a b])
; 
; (defrecord CssStyle [value])
; (defrecord SassStyle [value])
; (defrecord ScssStyle [value])
; (defrecord GakaStyle [value])
; 
; ; css-clj
; ; sass-clj
; ; scss-clj
; 
; (def css-example
; "
;   #navbar {
;     width: 80%;
;     height: 23px;
;   }
;   #navbar ul {
;     list-style-type: none;
;   }
;   #navbar li {
;     float: left;
;   }
;   #navbar li a {
;     font-weight: bold;
;   }
; ")
; 
; (def scss-example
; "
;   #navbar {
;     width: 80%;
;     height: 23px;
; 
;     ul {
;       list-style-type: none;
;     }
;     li {
;       float: left;
;       a {
;         font-weight: bold;
;       }
;     }
;   }
; ")
; 
; (def sass-example
; "
;   #navbar
;     width: 80%;
;     height: 23px;
; 
;     ul
;       list-style-type: none;
;     li
;       float: left;
;       a
;         font-weight: bold;
; ")

; import all
; convert foreign to native
; convert native to foreign
; export common

(defn parse-css [css]
  (if
    (empty? css)
    []
    (other-parse-css css "syntax")))

(defn emit-css [css-clj]
  (if
    (empty? css-clj)
    ""
    (clojure.string/trim (css css-clj))))

(defn parse-scss [scss] [])
(defn scss-to-css [scss] [])
(defn css-to-scss [css] [])
