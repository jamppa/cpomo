(ns cpomo.core
  (:use cpomo.gui)
  (:use cpomo.screen)
  (:import (java.awt.event ActionListener))
  (:import (javax.swing Timer))
  (:gen-class))

(declare pomodoro-task)

(defn start-pomodoro []
  (let [timer (Timer. 1000 pomodoro-task)]
    (.start timer)))

(def act-listener 
  (proxy [ActionListener] []
    (actionPerformed [event] 
      (disable-btn (.getSource event))
      (start-pomodoro))))

(def app-frame (ref (make-app-frame "Pomo v0.1")))
(def start-btn (ref (make-btn "Start" act-listener)))
(def pomo-lbl (ref (make-label "Press Start!")))
(def pomodoro-length 10) ; 1500

(defstruct pomodoro :seconds :text)

(defn format-pomodoro-str [seconds]
  (let [mins (int (/ seconds 60)) secs (mod seconds 60)]
    (format "%dm:%ds" mins secs)))

(defn print-pomodoro [{text :text}]
  (println text))

(defn create-pomodoro [seconds]
  (struct pomodoro seconds 
          (format-pomodoro-str seconds)))

(def current-pomodoro (ref (create-pomodoro pomodoro-length)))

(defn consume-pomodoro [{:keys [seconds] :as pomo}]
  (if (> seconds 0)
    (assoc pomo :seconds (dec seconds) 
           :text (format-pomodoro-str (dec seconds))) pomo))

(defn update-current-pomodoro []
  (dosync
    (alter current-pomodoro consume-pomodoro)))

(defn pomodoro-used? [{secs :seconds}]
  (= secs 0))

(defn reset-current-pomodoro []
  (dosync
    (ref-set current-pomodoro (create-pomodoro pomodoro-length))))

(defn on-pomodoro-used [pomodoro timer]
  (.stop timer)
  (enable-btn @start-btn)
  (reset-current-pomodoro)
  (block-screen))

(def pomodoro-task
  (proxy [ActionListener] []
    (actionPerformed [event] 
      (update-current-pomodoro)
      (print-pomodoro @current-pomodoro)
      (if (pomodoro-used? @current-pomodoro) 
        (on-pomodoro-used @current-pomodoro (.getSource event))))))

(defn -main [& args] 
  (init-frame @app-frame @start-btn @pomo-lbl)
  (show-frame @app-frame))

