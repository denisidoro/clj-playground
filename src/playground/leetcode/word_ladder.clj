(ns playground.leetcode.word-ladder)

(def begin-word "hit")
(def end-word "cog")
(def word-list ["hot" "dot" "dog" "lot" "log" "cog"])

(defn neighbour?
  [word other-word]
  (->> (map #(if (= %1 %2) 1 0) (seq word) (seq other-word))
       (reduce +)
       (= (-> word count dec))))

(defn best-match
  [haystack xs]
  (or (->> xs (filter #(= haystack %)) first)
      (first xs)))

(defn path
  [begin-word end-word word-list]
  (loop [path [begin-word]
         word begin-word
         dict word-list]
    (let [match (->> dict
                     (filter #(neighbour? word %))
                     (best-match end-word))]
      (cond
        (not match) path
        (= end-word match) (conj path match)
        :else (recur (conj path match) match (filter #(not= match %) dict))))))

(comment
  (path begin-word end-word word-list))
