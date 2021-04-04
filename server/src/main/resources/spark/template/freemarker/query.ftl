<#assign content>

<h1> Welcome to Stars!</h1>
<h5>
  Search By Name
  <label class="switch">
    <input id="toggle" onclick="getValue()" type="checkbox">
    <span class="slider round"></span>
  </label>
  Search
  By Location
</h5>

<div id = "form-wrapper">
  <div id = "form-left">
    <!--
  <div class="w3-bar w3-black">
    <button class="w3-bar-item w3-button" onclick="openCommand('NeighborsCommand')">NeighborsCommand Search</button>
    <button class="w3-bar-item w3-button" onclick="openCommand('Naive NeighborsCommand')">Naive</button>
  </div>
  -->

  <!--<div id = "NeighborsCommand" class = "command"> -->
    <h4>Neighbors Search</h4>
    <p id= "neighbors">
      <form id="neighborsform" method="POST" action="/neighbors">
        <label for="kr">number of neighbors: </label><br>
        <textarea name="kr" id="kNeighbors1" class = "words"></textarea><br>
        <label for="star">star name: </label><br>
        <textarea name="star" id="starNeighbors" class = "words"></textarea><br>
        <input type="submit" value="Find Stars">
      </form>

      <form id="neighborsform2" method="POST" action="/neighbors">
        <label for="kr">number of neighbors: </label><br>
        <textarea name="kr" id="kNeighbors2" class = "words"></textarea><br>
        <label for="x">x: </label><br>
        <textarea name="x" id="xNeighbors" class = "words"></textarea><br>
        <label for="y">y: </label><br>
        <textarea name="y" id="yNeighbors" class = "words"></textarea><br>
        <label for="z">z: </label><br>
        <textarea name="z" id="zNeighbors" class = "words"></textarea><br>
        <input type="submit" value="Find Stars">
      </form>
    </p>
  <!--</div> -->

  <!--<div id = "Naive NeighborsCommand" class = "command" style ="display:none"> -->
    <h4>Naive Neighbors Search</h4>
    <p id= "neighbors">
      <form id="naiveneighborsform" method="POST" action="/naive_neighbors">
        <label for="kr">number of neighbors: </label><br>
        <textarea name="kr" id="kNNeighbors" class = "words"></textarea><br>
        <label for="star">star name: </label><br>
        <textarea name="star" id="starNNeighbors" class = "words"></textarea><br>
        <input type="submit" value="Find Stars">
      </form>

      <form id="naiveneighborsform2" method="POST" action="/naive_neighbors">
        <label for="kr">number of neighbors: </label><br>
        <textarea name="kr" id="kNeighbors2" class = "words"></textarea><br>
        <label for="x">x: </label><br>
        <textarea name="x" id="xNeighbors" class = "words"></textarea><br>
        <label for="y">y: </label><br>
        <textarea name="y" id="yNeighbors" class = "words"></textarea><br>
        <label for="z">z: </label><br>
        <textarea name="z" id="zNeighbors" class = "words"></textarea><br>
        <input type="submit" value="Find Stars">
      </form>
    </p>
  </div>

  <div id="form-right">
    <h4>Radius Search</h4>
    <p id="radius">
      <form id="radiusform" method="POST" action="/radius">
        <label for="kr">radius: </label><br>
        <textarea name="kr" id="kRadius" class = "words"></textarea><br>
        <label for="star">star name: </label><br>
        <textarea name="star" id="starRadius" class = "words"></textarea><br>
        <input type="submit" value="Find Stars">
      </form>

      <form id="radiusform2" method="POST" action="/radius">
        <label for="kr">radius: </label><br>
        <textarea name="kr" id="kRadius2" class = "words"></textarea><br>
        <label for="x">x: </label><br>
        <textarea name="x" id="xRadius" class = "words"></textarea><br>
        <label for="y">y: </label><br>
        <textarea name="y" id="yRadius" class = "words"></textarea><br>
        <label for="z">z: </label><br>
        <textarea name="z" id="zRadius" class = "words"></textarea><br>
        <input type="submit" value="Find Stars">
      </form>
    </p>

    <h4>Naive Radius Search</h4>
    <p id="radius">
      <form id="naiveradiusform" method="POST" action="/naive_radius">
        <label for="kr">radius: </label><br>
        <textarea name="kr" id="rNRadius" class = "words"></textarea><br>
        <label for="star">star name: </label><br>
        <textarea name="star" id="starNRadius" class = "words"></textarea><br>
        <input type="submit" value="Find Stars">
      </form>

      <form id="naiveradiusform2" method="POST" action="/naive_radius">
        <label for="kr">radius: </label><br>
        <textarea name="kr" id="kRadius2" class = "words"></textarea><br>
        <label for="x">x: </label><br>
        <textarea name="x" id="xRadius" class = "words"></textarea><br>
        <label for="y">y: </label><br>
        <textarea name="y" id="yRadius" class = "words"></textarea><br>
        <label for="z">z: </label><br>
        <textarea name="z" id="zRadius" class = "words"></textarea><br>
        <input type="submit" value="Find Stars">
      </form>
    </p>
  </div>
</div>

<h2>Results Below!</h2>
<h3>${results}</h3>

</#assign>
<#include "main.ftl">