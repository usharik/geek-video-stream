let videoPlayer = document.getElementById("videoPlayer");

fetch("/currenttime")
    .then(resp => resp.text().then(ct => videoPlayer.currentTime = ct))
    .catch(reason => console.error(reason));

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

videoPlayer.ontimeupdate = () => {
    fetch("/savetime?currentTime=" + videoPlayer.currentTime, {method: 'POST'})
        .catch(reason => console.error(reason));
}

videoPlayer.onplay = () => {
    fetch("/savestate?state=play", {method: 'POST'})
        .catch(reason => console.error(reason));
}

videoPlayer.onpause = () => {
    fetch("/savestate?state=pause", {method: 'POST'})
        .catch(reason => console.error(reason));
}
