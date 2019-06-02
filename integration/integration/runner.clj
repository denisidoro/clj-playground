(ns integration.runner
  (:require [eftest.runner :as eftest]))

(defn log
  [x]
  (println (str "Running " (vec x)))
  x)

(defn main
  []
  (eftest/run-tests (log (eftest/find-tests "integration"))))
