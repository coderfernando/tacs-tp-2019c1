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

Vue.use(Router);
Vue.use(BootstrapVue);

const isLoggedIn = () => {
  //let token = sessionStorage.getItem("token");
  //return token !== undefined && token !== null;
  return true;
};

const router = new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: HomePage,
      meta: { auth: true }
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

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.auth)) {
    if (!isLoggedIn()) {
      // next({
      //   path: "/login",
      //   query: { redirect: to.fullPath }
      // });
      next();
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
