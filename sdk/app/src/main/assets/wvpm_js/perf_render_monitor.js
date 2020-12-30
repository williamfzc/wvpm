(function() {
  const times = [];
  let threshold = %s;

  function refreshLoop() {
    window.requestAnimationFrame(() => {
      const now = performance.now();
      times.push(now);

      if (times.length >= threshold) {
        wvpm.response(taskId, JSON.stringify(times))
        times = []
      }
      refreshLoop();
    });
  }

  refreshLoop();
})();
