package edu.cpp.cs3560.view;
import edu.cpp.cs3560.control.GameController;
import edu.cpp.cs3560.model.BoardCell;
import edu.cpp.cs3560.model.GameModel;
import edu.cpp.cs3560.model.Card;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private int difficulty = 1;
    private final HashMap<String, Color> themeColors = new HashMap<>(){{
	  put("GREEN", new Color(66, 131, 49));
	  put("BROWN", new Color(78, 50, 15));
	  put("CREAM", new Color(218, 175, 131));
    }};
    // Simple icons (replace with images if you’d like)
    private final Icon backIcon = iconColor(themeColors.get("GREEN"), "Matched!");
    private final Map<Integer, Icon> faceIcons = new HashMap<>();


    public SwingUI() {
	  chooseDifficulty();
	  // Build simple emoji “faces” per id:
	  setUpIcons();
	  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	  frame.setLayout(new BorderLayout(10,10));
	  JPanel top = new JPanel(new BorderLayout());
	  top.add(status, BorderLayout.WEST);
	  frame.add(top, BorderLayout.NORTH);
	  frame.add(grid, BorderLayout.CENTER);
	  grid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	  grid.setBackground(themeColors.get("BROWN"));
	  buildGrid();
	  frame.setSize(600, 650);
	  frame.setLocationRelativeTo(null);
	  frame.getContentPane().setBackground(themeColors.get("BROWN"));
	  frame.setVisible(true);
    }

    private void setUpIcons()
    {
	  if(difficulty == 1)
	  {
		String[] glyphs = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
		for (int i = 0; i < ROWS * COLS; i++)
		{
		    faceIcons.put(i, iconColor(themeColors.get("CREAM"), glyphs[i % glyphs.length]));
		}
	  }else if(difficulty == 2)
	  {

	  }else if(difficulty == 3)
	  {

	  }
    }
    private void buildGrid() {
	  for (int r=0;r<ROWS;r++) {
		for (int c=0;c<COLS;c++) {
		    JButton b = new JButton(faceIcons.get(model.getBoard().get(r,c).getId()));
		    b.setBackground(themeColors.get("CREAM"));
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
		    BoardCell a = model.getBoard().getCellAt(r, c); // not the first; so we must find both
			// Find a non-matched partner (the firstPick was internal)
			// Simpler: just flip back all faceUp but not matched & not this one after mismatch
			// But we have explicit method: we need both; so scan
		    BoardCell first = null, second = null;
		    outer:
		    for (int i=0;i<model.getBoard().getRows();i++)
			  for (int j=0;j<model.getBoard().getCols();j++) {
				BoardCell cell = model.getBoard().getCellAt(i,j);
				if (cell.isSelected() && !cell.isAllMatched()) {
				    if (first == null) first = cell;
				    else { second = cell; break outer; }
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
		    b.setBorder(BorderFactory.createLineBorder(themeColors.get("GREEN"), 4));
		}else
		{
		    b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
	  }
	  status.setText("Difficulty: " + difficulty + "   " + model.toString());
    }
    private Icon iconColor(Color bg, String text) {
	  int W=120, H=150;
	  Image img = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
	  Graphics2D g = (Graphics2D) img.getGraphics();
	  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		    RenderingHints.VALUE_ANTIALIAS_ON);
	  g.setColor(bg); g.fillRoundRect(0,0,W,H,20,20);
	  g.setColor(Color.BLACK);
	  g.setFont(new Font("SansSerif", Font.PLAIN, 25));
	  FontMetrics fm = g.getFontMetrics();
	  int tw = fm.stringWidth(text);
	  g.drawString(text, (W - tw)/2, H/2 + fm.getAscent()/2 - 8);
	  g.dispose();
	  return new ImageIcon(img);
    }

    private void chooseDifficulty()
    {
	  JDialog dialog = new JDialog(frame, "Choose Difficulty", true);
	  dialog.setForeground(themeColors.get("CREAM"));
	  dialog.setSize(300, 100);
	  JPanel panel = new JPanel();
	  dialog.setBackground(themeColors.get("BROWN"));
	  panel.setBackground(themeColors.get("BROWN"));
	  String[] options = {"Easy", "Medium", "Hard"};
	  JLabel label = new JLabel("Choose Difficulty");
	  label.setForeground(themeColors.get("CREAM"));
	  panel.add(label);
	  JComboBox selector = new JComboBox(options);
	  selector.setBackground(themeColors.get("CREAM"));
	  selector.setForeground(themeColors.get("GREEN"));
	  panel.add(selector);
	  selector.addItemListener(new ItemListener()
	  {
		@Override
		public void itemStateChanged(ItemEvent e) {
		    if(selector.getSelectedItem().equals("Hard"))
		    {
			  difficulty = 3;
		    }else if (selector.getSelectedItem().equals("Medium"))
		    {
			  difficulty = 2;
		    }
		}
	  });
	  JButton ok = new JButton("OK");
	  ok.setBackground(themeColors.get("GREEN"));
	  ok.addActionListener(e -> {
		dialog.dispose();
	  });
	  panel.add(ok);
	  dialog.add(panel);
	  dialog.setVisible(true);
    }
    public static void main(String[] args) {
	  SwingUtilities.invokeLater(SwingUI::new);
    }
}

