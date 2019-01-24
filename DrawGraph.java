import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.FontMetrics;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
//import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;



@SuppressWarnings("serial")
public class DrawGraph extends JPanel {
   private static final int MAX_SCORE = 50;
   private static final int PREF_W = 1100;
   private static final int PREF_H = 650;
   private static final int BORDER_GAP = 30;
   private static final Color GRAPH_COLOR = Color.green;
   private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
   private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
   private static final int GRAPH_POINT_WIDTH = 5;
   private static final int Y_HATCH_CNT = 40;
   private Find_Match searchClass;
    private List resultListDisplay;
   private ArrayList<Double> scores;
   private Integer frm2;
   private JLabel frameLabel;
   private Map<String, Integer> similarFrameMap;

   public DrawGraph(ArrayList<Double> scores1,Integer frm1) {
      this.scores = scores1;
      this.frm2=frm1;
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      double xScale = ((double) getWidth() - 2 * 30) / (450);
      double yScale = ((double) getHeight() - 2 * 30) / (MAX_SCORE - 1);

      List<Point> graphPoints = new ArrayList<Point>();
      for (int i = 0; i < scores.size(); i++) {
         int x1 = (int) (i * xScale + BORDER_GAP);
         int y1 = (int) ((MAX_SCORE - scores.get(i)) * yScale + BORDER_GAP);
         graphPoints.add(new Point(x1, y1));
      }

      // create x and y axes 
      g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
      g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

      // create hatch marks for y axis. 
      for (int i = 0; i < Y_HATCH_CNT; i++) {
         int x0 = BORDER_GAP;
         int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
         int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
         int y1 = y0;
         g2.drawLine(x0, y0, x1, y1);


      }
      FontMetrics fm = g2.getFontMetrics();
 int m=0;
      // and for x axis
      for (int i = 0; i < 450; i=i+50) {
         int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (449) + BORDER_GAP;
         int x1 = x0;
         int y0 = getHeight() - BORDER_GAP;
         int y1 = y0 - GRAPH_POINT_WIDTH;
         g2.drawLine(x0, y0, x1, y1);
        
         String value = Integer.toString(i);
         g2.drawString(value, x0 - (fm.stringWidth(value) / 4), y0 + fm.getAscent());
         g2.drawString("Frame number ", 40, y0 + fm.getAscent()+13 );
         String  errorsg = "Matching clip from " + (frm2+1) + " to " + (frm2+150) + ".";
         g2.drawString(errorsg, 100, 20 );
       //  m=m+100;
      }

      Stroke oldStroke = g2.getStroke();
      g2.setColor(GRAPH_COLOR);
      g2.setStroke(GRAPH_STROKE);
      for (int i = 0; i < graphPoints.size() - 1; i++) {
         int x1 = graphPoints.get(i).x;
         int y1 = graphPoints.get(i).y;
         int x2 = graphPoints.get(i + 1).x;
         int y2 = graphPoints.get(i + 1).y;
         g2.drawLine(x1, y1, x2, y2);         
      }

 //Graphics2D g21 = (Graphics2D)g;
         


      g2.setStroke(oldStroke);      
      g2.setColor(GRAPH_POINT_COLOR);

         AffineTransform at = new AffineTransform();
        at.setToRotation(Math.toRadians(90), 5, 40);
        g2.setTransform(at);
        g2.drawString("Sum of euclidien distances: ", 5, 40);

   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(PREF_W, PREF_H);
   }

   public void createAndShowGui(ArrayList<Double> scores1,Integer frm1) {

 Object obj = Collections.max(scores1);
 Object obj1 = Collections.min(scores1);
 // int x=Integer.parseInt(obj.toString());
 int x=0;
      

    System.out.println("max:"+ obj);
   System.out.println("min:"+ obj1);
 
 


      DrawGraph mainPanel = new DrawGraph(scores1,frm1);

       frameLabel = new JLabel("");
    /*   frameLabel.setForeground(Color.BLUE);
       

         String  errorsg = "The most similar clip is from frame " + (frm1+1) + " to frame " + (frm1+150) + ".";
       frameLabel.setText(errorsg);*/
      mainPanel.add(frameLabel);
      JFrame frame = new JFrame("Visual descriptor for similarity measure");
    //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      frame.getContentPane().add(mainPanel);
      frame.pack();
    
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
     // Object obj = Collections.max(score);

    // Object obj1 = Collections.min(psnr_sum);
   // System.out.println("max:"+ obj);
   //System.out.println("min:"+ obj1);
   }

   }