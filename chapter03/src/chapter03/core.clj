(ns chapter03.core)

;; Cursive custom shortcuts

;; barf backwards : ctrl+alt-s
;; barf forwards : alt+shift+t

;; slurp backwards : ctrl+alt+t
;; slurp forwards : alt+shift+s

;; send form te REPL: ctrl+è
;; jump to REPL : ctrl+à

;; switch repl ns to current file: alt+shift+r
;; sync files with REPL: alt+shift+m


(comment
  ; These are literal representations, a kind of valid form (AKA expression)
  1
  " a string"
  ["a" "vector" "of" "strings"]

  ; These are operations, another kind of valid form (AKA expression)
  ; Following this shape (operator operand1 operand2 ... operandN)
  (+ 1 2 3)
  (str "Hello" " " "World")

  ; Control flow with: if, do and when
  (if true
    "By Zeus' hammer!"
    "By Aqua-man's trident")

  (if false
    "By Zeus' hammer!"
    "By Aqua-man's trident")

  ; If we omit the else branch, we'll return nil
  (if false
    "By Odin's Elbow!")

  ; We can combine operations with `do`
  (if true
    (do (println "Success")
        "By Zeus' hammer!")
    (do (println "Failure")
        "By Aqua-man's trident!"))

  ; `when` is "if true do" (returns nil if false)
  (when true
    (do (println "Success")
        "By Zeus' hammer!")
    )

  ; We can check if a value is nil
  (nil? 1)                                                  ; => false
  (nil? nil)                                                ; => true

  ; nil or false is false, otherwise anything is truthy (like in Ruby)
  (if "wat"
    "yep"
    "nope")                                                 ; => "yep"

  ; Equality check
  (= 1 1)                                                   ; => true
  (= nil nil)                                               ; => true
  (= 1 2)                                                   ; => false


  ; or returns the first truthy value, otherwise the last value
  (or false "yep" :hello)                                   ; => "yep"
  (or false nil :hello)                                     ; => :hello

  ; and returns the last truthy value, otherwise the first falsy value
  (and 1 2 3)                                               ; => 3
  (and 1 nil 3)                                             ; => nil
  (and 1 2 false 4)                                         ; => false

  ; we can bind a name to a value with `def`
  (def failed-protagonist-names
    ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"])

  ; numbers
  93                                                        ; int
  1.2                                                       ; float
  1/5                                                       ; ratio

  ; Maps (AKA dictionaries)
  ; Keyword to String
  ; (type :wat)
  ;  => clojure.lang.Keyword
  ;(type "Hello")
  ;  => java.lang.String
  {:first-name "John" :last-name "Doe"}
  ; String to function
  ; (type +)
  ;  => clojure.core$_PLUS_
  {"add" +}

  ; We can also create dictionaries with `hash-map`
  ; Each key must be unique (or get a runtime error)
  (hash-map :first-name "John" :last-name "Doe")

  ; We can extract values with `get`
  (get (hash-map :first-name "John" :last-name "Doe") :first-name) ; => "John"

  ; We can look into nested maps with `get-in`
  (get-in {1 {:first-name "John" :last-name "Doe"}} [1 :first-name]) ; => "John"

  ; We can also index into a map like such
  ({1 {:first-name "John" :last-name "Doe"}} 1)

  ; Yet another way to index into a map
  ({:a 1 :b 2} :a)                                          ; => 1
  (:a {:a 1 :b 2})                                          ; => 1

  ; default value
  (:c {:a 1 :b 2} 99)                                       ; => 99

  ; index into a vector
  (get [3 2 1] 0)                                           ; => 3
  (get [3 2 1] 99)                                          ; => nil

  ; Another way to create vectors
  (vector 1 2 3)

  ; We can append elements to a vector with `conj`
  (conj [1 2 3] 4)                                          ; => [1 2 3 4]

  ; `conj` prepends elements to lists
  (conj '(1 2 3) 0)                                         ; => (0 1 2 3)

  ; We can create a set of unique values with `hash-set`
  ; A runtime is generated if there is a duplicate key when using the special syntax, otherwise ok when calling the
  ; hash-set function.
  #{:a :b :c}
  (hash-set :a :b :c)

  ; We can add another unique value do the setup, otherwise it's a NOOP
  (conj #{:a :b :c} :d)                                     ; => #{:c :b :d :a}
  (conj #{:a :b :c} :a)                                     ; => #{:c :b :a}

  ; Create a set from a vector with `set`
  (set [:a :b :c])                                          ; => #{:c :b :a}

  :rcf)

; defining a function taking a single parameter
(defn error-message
  [severity]
  (str "OH GOD! IT'S A DISASTER! WE'RE "
       (if (= severity :mild)
         "MILDLY INCONVENIENCED!"
         "DOOOOOOMED!")))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(comment
  ;; Functions

  ((or + -) 1 2 3)                                          ; => 6
  ((and (= 1 1) +) 1 2 3)                                   ; => 6
  ((first [+ 0]) 1 2 3)                                     ; => 6

  :rfc)


; Default arguments via mult-arity
; (my-inc 1)
;  => 2
; (my-inc 1 99)
;  => 101
(defn my-inc
  "Adds one more + an additional offset"
  ([n, offset] (+ n offset 1))
  ([n] (my-inc n 0))
  )

(defn greet
  [name]
  (str "Hello, " name "!"))

; We use the ampersand to make varargs
; (greet-all "John" "Mary" "Sue")
;  => ("Hello, John!" "Hello, Mary!" "Hello, Sue!")
(defn greet-all
  [& names]
  (map greet names)
  )

; Destructuring
; (my-first [1 2 3])
;  => 1
(defn my-first
  [[fst]]
  fst)

; (destruct-more [1 2 3 4 5])
;  => {:fst 1, :snd :snd, :rest (3 4 5)}
(defn destruct-more
  [[fst snd & rest]]
  {:fst fst :snd :snd :rest rest})

;(destruct-map {:lat 54.5 :lng 99.98})
;Treasure lat: 54.5
;Treasure lng: 99.98
;=> nil
(defn destruct-map
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(defn destruct-map2
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

; We describe anonymous functions with `fn`
(comment
  (map (fn [n] (* 2 n)) '(1 2 3))                           ; => (2 4 6)

  ; Another way
  ; This special syntax is made possible by a feature known called "reader macros"
  ; % is equivalent to %1
  (map #(* 2 %) '(1 2 3))                                   ; => (2 4 6)
  (map #(* 2 %1) '(1 2 3))                                  ; => (2 4 6)
  (#(str %1 %2) "inter" "galactic")                         ; => "intergalactic"
  ; %& refers to the "rest" (which is stored as a list)
  (#(str "("%1 ")" "["%2 "]" "=>" %&) "hello" "world" 1 2 3) ; => "(hello)[world]=>(1 2 3)"
  (#(str "("%1 ")" "=>" %&) "hello" "world" 1 2 3)          ; => "(hello)=>(\"world\" 1 2 3)"

  :rfc)

; We can define closures
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(comment

  (def inc3 (inc-maker 3))
  :rfc)
