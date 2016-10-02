package marinov.hristo.task5.utils;

/**
 * @author HristoMarinov (christo_marinov@abv.bg).
 */
public class FoodObj {

    private int _id;
    private String _name;
    private String _price;
    private int _rating;
    private byte[] image;

    public FoodObj(String _name, String _price, int _rating, byte[] image) {
        this._name = _name;
        this._price = _price;
        this._rating = _rating;
        this.image = image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }

    public int get_rating() {
        return _rating;
    }

    public void set_rating(int _rating) {
        this._rating = _rating;
    }
}
