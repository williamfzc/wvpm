// https://www.growingwiththeweb.com/2017/12/fast-simple-js-fps-counter.html
(function() {
  const times = [];
  let fps;

  function refreshLoop() {
    window.requestAnimationFrame(() => {
      const now = performance.now();
      while (times.length > 0 && times[0] <= now - 1000) {
        times.shift();
      }
      times.push(now);
      fps = times.length;
      console.log(`fps: ${fps}`)
      refreshLoop();
    });
  }

  refreshLoop();
})();
