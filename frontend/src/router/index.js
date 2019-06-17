import Vue from "vue";
import Router from "vue-router";
import HomePage from "@/pages/HomePage";
import PlacesPage from "@/pages/PlacesPage";
import ListsPage from "@/pages/ListsPage";
import SignUp from "@/pages/SignUpPage";
import SignIn from "@/pages/SignInPage";
import BootstrapVue from "bootstrap-vue";

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "../../static/css/main.css";
import axios from "axios";

Vue.use(Router);
Vue.use(BootstrapVue);

/* eslint-disable */
export function isLoggedIn() {
  return new Promise((resolve, reject) => {
    axios
      .post("/api/user/isuserlogged", {})
      .then(function() {
        resolve(true);
      })
      .catch(function() {
        resolve(false);
      });
  });
}
/* eslint-enable */

const router = new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: HomePage,
      meta: { auth: false }
    },
    {
      path: "/places",
      name: "Places",
      component: PlacesPage,
      meta: { auth: true }
    },
    {
      path: "/lists",
      name: "Lists",
      component: ListsPage,
      meta: { auth: true }
    },
    {
      path: "/signup",
      name: "SignUp",
      component: SignUp,
      meta: { auth: false }
    },
    {
      path: "/signin",
      name: "SignIn",
      component: SignIn,
      meta: { auth: false }
    }
  ]
});

router.beforeEach(async function checkLogin(to, from, next) {
  if (to.matched.some(record => record.meta.auth)) {
    var logged = await isLoggedIn();
    if (!logged) {
      window.location = "/login";
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
