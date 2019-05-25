package ar.edu.utn.tacs.model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserSession {

    private User user;
    private List<PlacesList> lists;

    // -------- Por ahora usamos singleton porque solo manejamos una sesion en memoria --------
    protected UserSession() {
        this.lists = new ArrayList<PlacesList>();
        User mockUser = new User();
        mockUser.setName("Manolo");
        mockUser.setPassword("password");
        this.user = mockUser;
    }

    private final static UserSession userSession = new UserSession();

    public static UserSession getInstance() { return userSession; }
    // ----------------

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PlacesList> getLists() {
        return lists;
    }

    public void setLists(List<PlacesList> lists) {
        this.lists = lists;
    }

    public PlacesList addList(PlacesList list) {
        lists.add(list);
        return list;
    }

    public PlacesList addPlaceToList(String place, String listId) {
        PlacesList list = lists.stream()
                .filter(x -> listId.equals(x.getId()))
                .findAny()
                .orElse(null);

        if (list != null) {
            list.addPlace(place);
        }
        return list;
    }

    public PlacesList changeListName(String name, String listId) {
        PlacesList list = lists.stream()
                .filter(x -> listId.equals(x.getId()))
                .findAny()
                .orElse(null);

        if (list != null) {
            list.setName(name);
        }
        return list;
    }

    public List<PlacesList>  deleteList(String listId) {
        List<PlacesList> placesLists = this.lists.stream()
                .filter(x -> !listId.equals(x.getId()))
                .collect(Collectors.toList());

        this.setLists(placesLists);
        return placesLists;
    }

    public PlacesList checkinPlaceInList(String placeId, String listId) {
        PlacesList list = lists.stream()
                .filter(x -> listId.equals(x.getId()))
                .findAny()
                .orElse(null);

        if (list != null) {
            list.checkin(placeId);
        }

        return list;
    }

}
