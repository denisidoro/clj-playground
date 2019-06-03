(ns integration.colls.ring-buffer-test
  (:require [clojure.test :as t]
            [matcher-combinators.matchers :as m]
            [matcher-combinators.test]
            [playground.colls.ring-buffer :as rb]
            [selvage.test.flow :refer [*world* defcheck defflow]]))

(defn init!
  [world]
  (assoc world :buffer (rb/new-naive-ring-buffer 3)))

(defn add!
  [x world]
  (update world :buffer rb/add-elem x))

(defn xs
  [world]
  (-> world :buffer :xs* deref seq))

(defflow naive-ring-buffer
  init!

  (t/testing "it starts empty"
    (t/is (match? nil (xs *world*))))

  (partial add! 1)

  (t/testing "a value can be added"
    (t/is (match? [1] (xs *world*))))

  (partial add! 2)
  (partial add! 3)
  (t/testing "it gets full"
    (t/is (match? [1 2 3] (xs *world*))))

  (partial add! 4)
  (t/testing "elements are cycled"
    (t/is (match? [2 3 5] (xs *world*)))))

