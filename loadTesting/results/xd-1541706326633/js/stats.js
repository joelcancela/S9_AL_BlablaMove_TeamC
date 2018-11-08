var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "2002",
        "ok": "1863",
        "ko": "139"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "1109",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "59128",
        "ok": "59128",
        "ko": "0"
    },
    "meanResponseTime": {
        "total": "19594",
        "ok": "21056",
        "ko": "0"
    },
    "standardDeviation": {
        "total": "15499",
        "ok": "15079",
        "ko": "0"
    },
    "percentiles1": {
        "total": "18214",
        "ok": "19357",
        "ko": "0"
    },
    "percentiles2": {
        "total": "34342",
        "ok": "34722",
        "ko": "0"
    },
    "percentiles3": {
        "total": "43404",
        "ok": "44353",
        "ko": "0"
    },
    "percentiles4": {
        "total": "57126",
        "ok": "57190",
        "ko": "0"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 0,
        "percentage": 0
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 2,
        "percentage": 0
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 1861,
        "percentage": 93
    },
    "group4": {
        "name": "failed",
        "count": 139,
        "percentage": 7
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "26.342",
        "ok": "24.513",
        "ko": "1.829"
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
        "total": "2002",
        "ok": "1863",
        "ko": "139"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "1109",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "59128",
        "ok": "59128",
        "ko": "0"
    },
    "meanResponseTime": {
        "total": "19594",
        "ok": "21056",
        "ko": "0"
    },
    "standardDeviation": {
        "total": "15499",
        "ok": "15079",
        "ko": "0"
    },
    "percentiles1": {
        "total": "18214",
        "ok": "19357",
        "ko": "0"
    },
    "percentiles2": {
        "total": "34342",
        "ok": "34722",
        "ko": "0"
    },
    "percentiles3": {
        "total": "43404",
        "ok": "44353",
        "ko": "0"
    },
    "percentiles4": {
        "total": "57126",
        "ok": "57190",
        "ko": "0"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 0,
        "percentage": 0
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 2,
        "percentage": 0
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 1861,
        "percentage": 93
    },
    "group4": {
        "name": "failed",
        "count": 139,
        "percentage": 7
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "26.342",
        "ok": "24.513",
        "ko": "1.829"
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
