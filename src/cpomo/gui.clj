(ns cpomo.gui
  (:use cpomo.screen)
  (:import (javax.swing JFrame JButton JLabel JPanel))
  (:import (java.awt BorderLayout Dimension Font))
  (:import (java.awt.event ActionListener))
  (:import (javax.swing Timer)))

(defn disable-btn [btn]
  (.setEnabled btn false))

(defn enable-btn [btn]
  (.setEnabled btn true))

(defn make-app-frame [title]
  (JFrame. title))

(defn make-btn [title listener]
  (let [btn (JButton. title)]
    (.setPreferredSize btn (Dimension. 100 50))
    (.addActionListener btn listener) btn))

(defn make-label [text]
  (let [label (JLabel. text)]
    (.setFont label (Font. "Arial" Font/PLAIN 26))
      label))

(defn add-component-to-frame [frame component area]
  (let [panel (.getContentPane frame)]
    (.add panel component area)))

(defn make-panel [width height]
  (let [panel (JPanel.)]
    (.setSize panel width height) panel))

(defn init-frame [frame btn lbl]
  (.setSize frame 350 200)
  (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE)
  (add-component-to-frame frame btn BorderLayout/SOUTH)
  (add-component-to-frame frame lbl BorderLayout/CENTER))

(defn show-frame [frame]
  (.pack frame)
  (.setVisible frame true))


