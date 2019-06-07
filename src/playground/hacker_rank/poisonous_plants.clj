(ns playground.hacker-rank.poisonous-plants
  (:import (java.util ArrayList)))

(def p [3 6 2 7 5])

(defn survivals
  [p]
  (let [s* (ArrayList.)]
    (loop [previous nil
           [current & others] p]
      (if-not current
        s*
        (do (if (or (not previous)
                    (<= current previous))
              (.add s* current))
            (recur current others))))))

(defn poisonousPlants
  [p]
  (loop [n      0
         plants p]
    (let [survs (survivals plants)]
      (if (= survs plants)
        n
        (recur (inc n) survs)))))

(survivals p)
