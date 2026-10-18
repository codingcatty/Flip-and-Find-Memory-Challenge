import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class MemoryCard extends JButton {

    private GameItem item;
    private boolean matched = false;
    private boolean revealed = false;

    public MemoryCard(GameItem item) {
        this.item = item;

        setText("?");
        setFont(new Font("SansSerif", Font.BOLD, 35));
        setBackground(Color.YELLOW);
        setFocusPainted(false);
        setOpaque(true);
    }

    public String getSymbol() {
        return item.getSymbol();
    }

    public boolean isMatched() {
        return matched;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        revealed = true;
        setText(item.getSymbol());
        setBackground(Color.WHITE);
    }

    public void hideCard() {
        if (!matched) {
            revealed = false;
            setText("?");
            setBackground(Color.YELLOW);
        }
    }

    public void setMatched() {
        matched = true;
        revealed = true;
        setBackground(Color.GREEN);
        setEnabled(false);
    }
}