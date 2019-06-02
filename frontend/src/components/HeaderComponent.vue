<template>
  <div class="row">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="/">
        <img
          src="../../static/img/logo.png"
          width="100"
          height="30"
          class="d-inline-block align-top"
          alt=""
        />
      </a>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarNavDropdown"
        aria-controls="navbarNavDropdown"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div id="navbarNavDropdown" class="collapse navbar-collapse">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="login">
              LogIn
              <span class="sr-only"></span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="logout">
              LogOut
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/signup">
              SignUp
            </a>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: "Header"
};
</script>

<style></style>
