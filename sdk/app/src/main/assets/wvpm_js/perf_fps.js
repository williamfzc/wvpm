// based on https://www.growingwiththeweb.com/2017/12/fast-simple-js-fps-counter.html
(function() {
  console.log("start tracing fps ...")
  const times = [];
  let fps;
  let threshold = %s;
  let count = 0

  function refreshLoop() {
    window.requestAnimationFrame(() => {
      const now = performance.now();

      while (times.length > 0 && times[0] <= now - 1000) {
        times.shift();
      }

      // almost redraw, skip the first 60 too
      if (times.length <= 5) {
        count = 0
      }

      times.push(now);
      fps = times.length;

      // skip the first 60 frames
      if (count < 60) {
        count ++
      }
      if (count >= 60 && fps < threshold) {
        wvpm.response(taskId, `current fps: ${fps}, threshold: ${threshold}`)
      }
      refreshLoop();
    });
  }

  refreshLoop();
})();
