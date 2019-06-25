(ns playground.money.easy
  (:require [quark-beta.conversion.data :as conversion]
            [clojure.string :as str]
            [quark-beta.string.core :as string]
            [quark.collection.seq :as seq]
            [quark.collection.map :as map]
            [clj-time.core :as time]))

(def path
  (str (System/getenv "HOME") "/dev/money/investments.json"))

(def content
  (-> path
      slurp
      conversion/json->edn))

(def investment
  (-> content :investiments (nth 4)))

investment

(defn relevant-word?
  [x]
  (and (-> x count (> 3))
       (-> x #{"banco"} not)))

(defn date
  [iso-str]
  (try
    (-> iso-str
        str
        (subs 0 10))
    (catch Exception _
      nil)))

(defn id
  [{:keys [securityType nickName maturity]}]
  (let [words (-> nickName
                  str/lower-case
                  (str/replace "/" "-")
                  (str/split #"\s"))
        nick  (seq/find-first relevant-word? words)
        date  (some-> maturity
                      date
                      (str/replace "-" "")
                      (subs 2))
        base  (if (seq date)
                (str securityType "/" nick "." date)
                (str securityType "/" nick))]
    (-> base
        str/lower-case
        (str/replace " " "")
        string/normalize
        keyword)))

(id investment)



(defn rate
  [txt]
  (try
    (as-> txt it
          (str/replace it "," ".")
          (str/replace it #"[^\d\. ]" "")
          (str/split it #" ")
          (seq/find-first #(re-find #"\d" %) it)
          (bigdec it)
          (/ it 100M))
    (catch Exception _
      nil)))

(defn definition
  [{:keys [grossValue investedCapital operationalDate maturity rentability] :as investment}]
  (let [investment-id    (id investment)
        operational-date (date operationalDate)
        maturity-date    (date maturity)
        rentability-rate (rate rentability)]
    [investment-id
     :parent-bucket
     investedCapital
     operational-date
     maturity-date
     nil
     rentability-rate]))

(defn definitions
  [content]
  (->> content
       :investiments
       (map definition)
       (map :definition outs)))

(defn history
  [content]
  (->> content
       :investiments
       (map (fn [{:keys [grossValue] :as investment}] [(id investment) grossValue]))
       (into {})))

(definitions content)
(history content)
