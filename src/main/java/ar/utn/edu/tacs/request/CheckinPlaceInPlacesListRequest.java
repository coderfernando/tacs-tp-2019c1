package ar.utn.edu.tacs.request;

public class CheckinPlaceInPlacesListRequest {

    private long listId;
    private long placeId;

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }
}
