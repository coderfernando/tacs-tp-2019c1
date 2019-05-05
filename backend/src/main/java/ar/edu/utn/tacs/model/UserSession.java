package ar.edu.utn.tacs.model;

import ar.edu.utn.tacs.model.places.Venue;

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

    public void addPlaceToList(Venue place, Long listId) {
        PlacesList list = lists.stream()
                               .filter(x -> listId.equals(x.getId()))
                               .findAny()
                               .orElse(null);

        if (list != null) {
            list.addPlace(place);
        }
    }

    public void changeListName(String name, Long listId) {
        PlacesList list = lists.stream()
                                .filter(x -> listId.equals(x.getId()))
                                .findAny()
                                .orElse(null);

        if (list != null) {
            list.setName(name);
        }
    }

    public void deleteList(Long listId) {
        List<PlacesList> placesLists = this.lists.stream()
                                                  .filter(x -> !listId.equals(x.getId()))
                                                  .collect(Collectors.toList());

        setLists(placesLists);
    }

    public void checkinPlaceInList(String placeId, Long listId) {
        PlacesList list = lists.stream()
                                .filter(x -> listId.equals(x.getId()))
                                .findAny()
                                .orElse(null);

        if (list != null) {
            list.checkin(placeId);
        }
    }

}
