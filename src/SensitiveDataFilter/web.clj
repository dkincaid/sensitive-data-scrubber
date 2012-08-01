(ns SensitiveDataFilter.web
  (:require [SensitiveDataFilter.core :as core]
            [noir.server :as server]
            [noir.response :as response])
  (:use [noir.core]
        [hiccup.core]
        [hiccup.form]))

(defpartial layout [& content]
  (html
   [:head
    [:title "Sensitive Data Scrubber"]]
   [:body
    [:h1 "Sensitive Data Scrubber"]
    content]))

(defpartial fields []
  (label "text" "Text to scrub: ")
  (text-field "text"))

(defpartial scrubbed-data [scrubbed-value]
  (label "scrubbed-text" "Your scrubbed text: ")
  (text-field "scrubbed-text" scrubbed-value))

(defpage "/" {:as scrubbed-text}
  (layout
   (form-to [:post "/"]
            (fields)
            (submit-button "Scrub")
            (html [:p])
            (scrubbed-data scrubbed-text)
            )))

(defpage [:post "/"] {:keys [text]}
  (render "/" (core/remove-sensitive-data text)))

(defn -main [port]
  (server/start (Integer/parseInt port)))