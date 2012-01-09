(ns cpomo.screen
  (:import (java.awt Robot Rectangle Toolkit))
  (:import (javax.swing JOptionPane)))

(defn say-hello []
  (JOptionPane/showMessageDialog
    nil "Moro!" "Tervehdys" JOptionPane/INFORMATION_MESSAGE))

(defn take-screenshot [] 
  (let [robot (Robot.)]
    (.createScreenCapture robot 
      (Rectangle. (.. Toolkit getDefaultToolkit getScreenSize)))))


