var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "640",
        "ok": "640",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "8",
        "ok": "8",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "5802",
        "ok": "5802",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "961",
        "ok": "961",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "1320",
        "ok": "1320",
        "ko": "-"
    },
    "percentiles1": {
        "total": "381",
        "ok": "381",
        "ko": "-"
    },
    "percentiles2": {
        "total": "1180",
        "ok": "1180",
        "ko": "-"
    },
    "percentiles3": {
        "total": "4578",
        "ok": "4578",
        "ko": "-"
    },
    "percentiles4": {
        "total": "5332",
        "ok": "5332",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 413,
        "percentage": 65
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 74,
        "percentage": 12
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 153,
        "percentage": 24
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "20.645",
        "ok": "20.645",
        "ko": "-"
    }
},
contents: {
"req_login-99dea": {
        type: "REQUEST",
        name: "Login",
path: "Login",
pathFormatted: "req_login-99dea",
stats: {
    "name": "Login",
    "numberOfRequests": {
        "total": "320",
        "ok": "320",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "8",
        "ok": "8",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "5802",
        "ok": "5802",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "600",
        "ok": "600",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "828",
        "ok": "828",
        "ko": "-"
    },
    "percentiles1": {
        "total": "297",
        "ok": "297",
        "ko": "-"
    },
    "percentiles2": {
        "total": "1004",
        "ok": "1004",
        "ko": "-"
    },
    "percentiles3": {
        "total": "1461",
        "ok": "1461",
        "ko": "-"
    },
    "percentiles4": {
        "total": "5426",
        "ok": "5426",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 230,
        "percentage": 72
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 42,
        "percentage": 13
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 48,
        "percentage": 15
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "10.323",
        "ok": "10.323",
        "ko": "-"
    }
}
    },"req_logout-0323d": {
        type: "REQUEST",
        name: "Logout",
path: "Logout",
pathFormatted: "req_logout-0323d",
stats: {
    "name": "Logout",
    "numberOfRequests": {
        "total": "320",
        "ok": "320",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "10",
        "ok": "10",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "5345",
        "ok": "5345",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "1322",
        "ok": "1322",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "1593",
        "ok": "1593",
        "ko": "-"
    },
    "percentiles1": {
        "total": "593",
        "ok": "593",
        "ko": "-"
    },
    "percentiles2": {
        "total": "1362",
        "ok": "1362",
        "ko": "-"
    },
    "percentiles3": {
        "total": "4959",
        "ok": "4959",
        "ko": "-"
    },
    "percentiles4": {
        "total": "5230",
        "ok": "5230",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 183,
        "percentage": 57
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 32,
        "percentage": 10
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 105,
        "percentage": 33
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "10.323",
        "ok": "10.323",
        "ko": "-"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
