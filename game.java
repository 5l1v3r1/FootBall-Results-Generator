package frg;

public class game
{
    int homeScore, awayScore;
    String homeName, awayName;
 
    public game(String hn, String an, int hs, int as)
    {
        homeScore = hs; awayScore = as;
        homeName = hn; awayName = an;
    }
   
    public void print()
    {
        System.out.println(homeName + " [" + homeScore + "]" + " | " + awayName + " [" + awayScore + "]");
    }
}