(ns cpomo.screen
  (:import (java.awt Robot Rectangle Toolkit))
  (:import (javax.swing JOptionPane JFrame)))

(defn take-screenshot [] 
  (let [robot (Robot.)]
    (.createScreenCapture robot 
      (Rectangle. (.. Toolkit getDefaultToolkit getScreenSize)))))

(defn image-panel [image x y]
  (proxy [JFrame] []
    (paint [g]
      (.drawImage g image x y this))))

