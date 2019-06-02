(ns playground.runner
  (:require [eftest.runner :as eftest]))

(defn main
  []
  (eftest/run-tests (eftest/find-tests "test")))
