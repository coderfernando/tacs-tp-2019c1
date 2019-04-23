package ar.utn.edu.tacs.model;

import ar.utn.edu.tacs.model.places.Venue;

import java.util.List;
import java.util.stream.Collectors;

public class UserSession {

    private User user;
    private List<PlacesList> lists;

    // -------- Por ahora usamos singleton porque solo manejamos una sesion en memoria --------
    protected UserSession() {}

    private final static UserSession userSession = new UserSession();

    public static UserSession getInstance() {
        return userSession;
    }
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

    public void addList(PlacesList list) {
        lists.add(list);
    }

    public void addPlaceToList(Venue place, long listId) {
        PlacesList list = lists.stream()
                               .filter(x -> listId == (x.getId()))
                               .findAny()
                               .orElse(null);

        if (list != null) {
            list.addPlace(place);
        }
    }

    public void changeListName(String name, long listId) {
        PlacesList list = lists.stream()
                                .filter(x -> listId == (x.getId()))
                                .findAny()
                                .orElse(null);

        if (list != null) {
            list.setName(name);
        }
    }

    public void deleteList(long listId) {
        List<PlacesList> placesLists = this.lists.stream()
                                                  .filter(x -> listId !=(x.getId()))
                                                  .collect(Collectors.toList());

        setLists(placesLists);
    }

    public void checkinPlaceInList(String placeId, long listId) {
        PlacesList list = lists.stream()
                                .filter(x -> listId == (x.getId()))
                                .findAny()
                                .orElse(null);

        if (list != null) {
            list.checkin(placeId);
        }
    }

}
