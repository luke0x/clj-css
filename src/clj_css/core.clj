(ns clj-css.core
  (:use [clojure.test]
        [clojure.string :only [blank? trim]]))

; https://github.com/programble/csslj/blob/master/src/csslj/core.clj

(defn- render-ele [ele]
  (->> ele (map name) (interpose " ") (apply str)))

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
     (->> (map css (cons vec more)) (interpose " ") (apply str))))

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
    [:h1 {:color "#abcdef"}]))

(defn emit-css [css-clj]
  (if
    (empty? css-clj)
    ""
    (trim (css css-clj))))

(defn parse-scss [scss] [])
(defn scss-to-css [scss] [])
(defn css-to-scss [css] [])
