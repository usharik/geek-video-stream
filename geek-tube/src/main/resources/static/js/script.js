let videoPlayer = document.getElementById("videoPlayer");

let ct = sessionStorage.getItem("currentTime");
if (ct != null) {
    videoPlayer.currentTime = ct;
} else {
    fetch("/currenttime")
        .then(resp => resp.text().then(ct => {
            videoPlayer.currentTime = ct;
            sessionStorage.setItem("currentTime", ct);
        }))
        .catch(reason => console.error(reason));
}

fetch("/currentstate")
    .then(resp => resp.text()
        .then(state => {
            console.info(state);
            if (state === 'play') {
                videoPlayer.play();
            } else if (state === 'pause') {
                videoPlayer.pause();
            }
        })
    )
    .catch(reason => console.error(reason));

let lastSave = videoPlayer.currentTime;

videoPlayer.ontimeupdate = () => {
    sessionStorage.setItem("currentTime", videoPlayer.currentTime);

    if (Math.abs(videoPlayer.currentTime - lastSave) > 10) {
        lastSave = videoPlayer.currentTime;
        fetch("/savetime?currentTime=" + videoPlayer.currentTime, {method: 'POST'})
            .catch(reason => console.error(reason));
    }
}

videoPlayer.onplay = () => {
    fetch("/savestate?state=play", {method: 'POST'})
        .catch(reason => console.error(reason));
}

videoPlayer.onpause = () => {
    fetch("/savestate?state=pause", {method: 'POST'})
        .catch(reason => console.error(reason));
}
