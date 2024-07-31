(ns clojure-noob.core
  (:gen-class))

; rg --files src/ | entr -cr lein run
;
; lein uberjar
; java -jar target/uberjar/clojure-noob-0.1.0-SNAPSHOT-standalone.jar


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "I'm a little teapot!"))
