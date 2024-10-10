<template src="./Landscape.html"></template>

<script lang="ts" src="./Landscape.component.ts"></script>

<style scoped>
.jhipster-landscape-search-container {
  position: absolute;
  top: 20px;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  z-index: 10;
}

.jhipster-landscape-search {
  display: flex;
  align-items: center;
  background-color: white;
  padding: 10px;
  border-radius: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.jhipster-landscape-search .jhlite-field--field {
  width: 300px;
  margin-right: 10px;
}

.jhipster-landscape-search .jhlite-input-text {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.jhipster-landscape-search-button {
  padding: 8px 16px;
  font-size: 1rem;
  cursor: pointer;
  border-radius: 4px;
}

/* Adjust the main content to accommodate the search bar */
.jhipster-landscape {
  padding-top: 80px; /* Adjust this value based on the height of your search bar */
}

.-search-highlighted {
  position: relative;
  box-shadow: 0 0 0 3px #ffd700; /* Solid gold border */
  z-index: 1;
  background-color: rgba(255, 215, 0, 0.1); /* Light gold background */
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(255, 215, 0, 0.7);
  }
  70% {
    box-shadow: 0 0 0 10px rgba(255, 215, 0, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(255, 215, 0, 0);
  }
}
</style>
