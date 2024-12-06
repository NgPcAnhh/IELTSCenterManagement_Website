package model;

public class Ranking {
    private String id;           // ID của người dùng
    private float overall;       // Điểm overall
    private int rank;            // Thứ hạng
    private float betterThan;    // Tỷ lệ phần trăm học sinh có điểm thấp hơn
    private float worseThan;     // Tỷ lệ phần trăm học sinh có điểm cao hơn

    // Constructor không tham số
    public Ranking() {}

    // Constructor đầy đủ tham số
    public Ranking(String id, float overall, int rank, float betterThan, float worseThan) {
        this.id = id;
        this.overall = overall;
        this.rank = rank;
        this.betterThan = betterThan;
        this.worseThan = worseThan;
    }

    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getOverall() {
        return overall;
    }

    public void setOverall(float overall) {
        this.overall = overall;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getBetterThan() {
        return betterThan;
    }

    public void setBetterThan(float betterThan) {
        this.betterThan = betterThan;
    }

    public float getWorseThan() {
        return worseThan;
    }

    public void setWorseThan(float worseThan) {
        this.worseThan = worseThan;
    }
}
