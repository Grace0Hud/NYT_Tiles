package edu.cpp.cs3560.view;
import edu.cpp.cs3560.control.GameController;
import edu.cpp.cs3560.model.GameModel;
import edu.cpp.cs3560.model.Card;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
public class SwingUI
{
    private static final int ROWS = 4, COLS = 4;
    private final JFrame frame = new JFrame("New York Times Tile Match - Swing");
    private final JPanel grid = new JPanel(new GridLayout(ROWS, COLS, 8, 8));
    private final JLabel status = new JLabel("Find all matches!");
    private final JButton[][] buttons = new JButton[ROWS][COLS];
    private final GameModel model = new GameModel(ROWS, COLS, System.nanoTime());
    private final GameController controller = new GameController(model);
    // Simple icons (replace with images if you’d like)
    private final Icon backIcon = iconColor(new Color(60,90,150), "￿");
    private final Map<Integer, Icon> faceIcons = new HashMap<>();


    public SwingUI() {
	// Build simple emoji “faces” per id:
	  String[] glyphs = {"A","B","C","D","E","F","G","H","I","J","K"};
	  for (int i = 0; i < ROWS*COLS; i++)
	  {
		faceIcons.put(i, iconColor(new Color(230, 230, 230), glyphs[i%glyphs.length]));
	  }
	  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	  frame.setLayout(new BorderLayout(10,10));
	  JPanel top = new JPanel(new BorderLayout());
	  top.add(status, BorderLayout.WEST);
	  frame.add(top, BorderLayout.NORTH);
	  frame.add(grid, BorderLayout.CENTER);
	  grid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	  buildGrid();
	  frame.setSize(600, 650);
	  frame.setLocationRelativeTo(null);
	  frame.setVisible(true);
    }
    private void buildGrid() {
	  for (int r=0;r<ROWS;r++) {
		for (int c=0;c<COLS;c++) {
		    JButton b = new JButton(faceIcons.get(model.getBoard().get(r,c).getId()));
		    b.setFocusPainted(false);
		    int rr = r, cc = c;
		    b.addActionListener(e -> onClick(rr, cc));
		    buttons[r][c] = b;
		    grid.add(b);
		}
	  }
	  refresh();
    }

    private void onClick(int r, int c) {
	  controller.onCardClicked(r, c, () -> {
		// Schedule a delay using Swing Timer
		new javax.swing.Timer(700, e -> {
		    Card a = model.getBoard().get(r, c); // not the first; so we must find both
			// Find a non-matched partner (the firstPick was internal)
			// Simpler: just flip back all faceUp but not matched & not this one after mismatch
			// But we have explicit method: we need both; so scan
		    Card first = null, second = null;
		    outer:
		    for (int i=0;i<model.getBoard().getRows();i++)
			  for (int j=0;j<model.getBoard().getCols();j++) {
				Card card = model.getBoard().get(i,j);
				if (card.isSelected() && !card.isMatched()) {
				    if (first == null) first = card;
				    else { second = card; break outer; }
				}
			  }
		    if (first != null && second != null) {
			  controller.resolveMismatch(first, second);
		    }
		    refresh();
		}) {{ setRepeats(false); }}.start();
	  });
	  refresh();
	  if (controller.isWin()) status.setText("You win! " + model.toString());
    }
    private void refresh() {
	  for (int r=0;r<ROWS;r++) for (int c=0;c<COLS;c++)
	  {
		Card card = model.getBoard().get(r,c);
		JButton b = buttons[r][c];
		if(card.isMatched())
		{
		    b.setIcon(backIcon);
		}else if(card.isSelected())
		{
		    b.setBorder(new LineBorder(Color.RED));
		}else
		{
		    b.setBorder(new LineBorder(Color.BLACK));
		}
	  }
	  status.setText(model.toString());
    }
    private Icon iconColor(Color bg, String text) {
	  int W=120, H=150;
	  Image img = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
	  Graphics2D g = (Graphics2D) img.getGraphics();
	  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		    RenderingHints.VALUE_ANTIALIAS_ON);
	  g.setColor(bg); g.fillRoundRect(0,0,W,H,20,20);
	  g.setColor(Color.BLACK);
	  g.setFont(new Font("SansSerif", Font.PLAIN, 48));
	  FontMetrics fm = g.getFontMetrics();
	  int tw = fm.stringWidth(text);
	  g.drawString(text, (W - tw)/2, H/2 + fm.getAscent()/2 - 8);
	  g.dispose();
	  return new ImageIcon(img);
    }
    public static void main(String[] args) {
	  SwingUtilities.invokeLater(SwingUI::new);
    }
}

