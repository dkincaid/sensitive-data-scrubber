(ns SensitiveDataFilter.web
  (:require [SensitiveDataFilter.core :as core]
            [noir.server :as server]
            [noir.response :as response])
  (:use [noir.core]
        [hiccup.core]
        [hiccup.form]
        [hiccup.element]))

(def test-cards ["378282246310005" "371449635398431" "378734493671000" "5610591081018250"
                 "30569309025904" "38520000023237" "6011111111111117" "6011000990139424"
                 "3530111333300000" "3566002020360505" "5555555555554444" "5105105105105100"
                 "4111111111111111" "4012888888881881" "4222222222222"])

(defpartial layout [& content]
  (html
   [:head
    [:title "Sensitive Data Scrubber"]]
   [:body
    [:h1 "Sensitive Data Scrubber"]
    content]))

(defpartial fields []
  (label "text" "Text to scrub: ")
  (text-field {:width 40} "text"))

(defpartial scrubbed-data [scrubbed-value]
  (label "scrubbed-text" "Scrubbed text: ")
  (text-field {:width 40}  "scrubbed-text" scrubbed-value))

(defpartial sample-cards []
  (html
   [:p "Test credit card numbers:"]
   [:ul
    (map #(vector :li %) test-cards)]
   (link-to "https://www.paypalobjects.com/en_US/vhelp/paypalmanager_help/credit_card_numbers.htm" "Source")))

(defpage "/" {:as scrubbed-text}
  (layout
   (form-to [:post "/"]
            (fields)
            (submit-button "Scrub")
            (html [:p])
            (scrubbed-data scrubbed-text)
            (sample-cards)
            )))

(defpage [:post "/"] {:keys [text]}
  (render "/" (core/remove-sensitive-data text)))

(defn -main [port]
  (server/start (Integer/parseInt port)))