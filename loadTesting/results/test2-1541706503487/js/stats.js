var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "1502",
        "ok": "1502",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "78",
        "ok": "78",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "11645",
        "ok": "11645",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "5284",
        "ok": "5284",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "2123",
        "ok": "2123",
        "ko": "-"
    },
    "percentiles1": {
        "total": "5215",
        "ok": "5215",
        "ko": "-"
    },
    "percentiles2": {
        "total": "6430",
        "ok": "6430",
        "ko": "-"
    },
    "percentiles3": {
        "total": "9294",
        "ok": "9294",
        "ko": "-"
    },
    "percentiles4": {
        "total": "10205",
        "ok": "10205",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 42,
        "percentage": 3
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 15,
        "percentage": 1
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 1445,
        "percentage": 96
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "68.273",
        "ok": "68.273",
        "ko": "-"
    }
},
contents: {
"req_createdelivery-228c8": {
        type: "REQUEST",
        name: "createDelivery",
path: "createDelivery",
pathFormatted: "req_createdelivery-228c8",
stats: {
    "name": "createDelivery",
    "numberOfRequests": {
        "total": "1502",
        "ok": "1502",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "78",
        "ok": "78",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "11645",
        "ok": "11645",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "5284",
        "ok": "5284",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "2123",
        "ok": "2123",
        "ko": "-"
    },
    "percentiles1": {
        "total": "5215",
        "ok": "5215",
        "ko": "-"
    },
    "percentiles2": {
        "total": "6430",
        "ok": "6430",
        "ko": "-"
    },
    "percentiles3": {
        "total": "9294",
        "ok": "9294",
        "ko": "-"
    },
    "percentiles4": {
        "total": "10205",
        "ok": "10205",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 42,
        "percentage": 3
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 15,
        "percentage": 1
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 1445,
        "percentage": 96
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "68.273",
        "ok": "68.273",
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
