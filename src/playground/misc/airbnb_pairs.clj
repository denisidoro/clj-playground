(ns playground.misc.airbnb-pairs
  (:require [clojure.test :as t]))

(def data
  {:a [:c :d]
   :b [:d :a :c]
   :c [:a :b]
   :d [:c :a :b]})

(defn has_mutual_pair_for_rank
  [rank data user]
  (let [other-user               (get (get data user) rank)
        other-users-first-choice (get (get data other-user) rank)]
    (= other-users-first-choice user)))

(def has_mutual_first_choice
  (partial has_mutual_pair_for_rank 0))

(defn safe-subvec
  [xs n]
  (if (>= n (count xs))
    []
    (subvec xs n)))

(defn swap-choices
  [xs n]
  (-> (subvec xs 0 (dec n))
      (conj (get xs n))
      (conj (get xs (dec n)))
      (into (safe-subvec xs (inc n)))))

(defn changed_pairings
  [rank data user]
  (let [originally-mutually-ranked? (has_mutual_pair_for_rank rank data user)
        swaped-data                 (update data user #(swap-choices % rank))
        mutually-ranked?            (has_mutual_pair_for_rank (dec rank) swaped-data user)]
    (if (not= mutually-ranked? originally-mutually-ranked?)
      [(get (get data user) rank)]
      [])))

(t/deftest swap
  (t/are [input n output]
    (= output (swap-choices input n))
    [:d :a :c] 1 [:a :d :c]))

(t/deftest has-mutual
  (t/are [input output]
    (= output (has_mutual_first_choice data input))
    :a true
    :b false))

(t/deftest nth-choice
  (t/are [user n output]
    (= output (has_mutual_pair_for_rank n data user))
    :a 0 true
    :a 1 true
    :b 0 false))

(t/deftest changes
  (t/are [user n output]
    (= output (changed_pairings n data user))
    :d 1 [:a]
    :b 2 [:c]
    :b 1 []))

(comment
  (t/run-tests))

