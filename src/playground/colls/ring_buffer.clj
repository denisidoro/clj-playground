(ns playground.colls.ring-buffer)

(defprotocol RingBuffer
  (add-elem [this x]))

(defn ^:private safe-subvec
  [xs start]
  (if (pos? start)
    (subvec xs start)
    xs))

(defn ^:private move
  [size x xs]
  (-> (conj xs x)
      (safe-subvec (- (count xs) size -1))))

(defrecord NaiveRingBuffer [size xs*]
  RingBuffer
  (add-elem [this x]
    (swap! xs* (partial move size x))
    this))

(defn new-naive-ring-buffer
  [size]
  (->NaiveRingBuffer size (atom [])))

