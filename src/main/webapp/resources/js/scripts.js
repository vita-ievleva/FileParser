$(document).ready(function () {
    'use strict';
    function sortServersName(a, b) {
        var nameA = a.name.toLowerCase(), nameB = b.name.toLowerCase();
        if (nameA < nameB) //sort string ascending
            return -1;
        if (nameA > nameB)
            return 1;
        return 0;
    }
    function sortAppsName(a, b) {
        var nameA = a.toLowerCase(), nameB = b.toLowerCase();
        if (nameA < nameB) //sort string ascending
            return -1;
        if (nameA > nameB)
            return 1;
        return 0;
    }
    function getServersList(data) {
        var toExclude = [];
        data = data.servers.sort(sortServersName);

        console.log(data)

        $("#search_servers_to option").each(function (i, item) {
            console.log(item.textContent)
            toExclude.push(item.textContent)
        });

        data = data.filter(function (server) {
            console.log(server.name)

            return toExclude.indexOf(server.name) < 0
        });

        console.log(data)

        console.log("excluded")
        console.log(toExclude)
        var items = [];

        $.each(data, function (key, element) {
            items.push('<option value="' + element.name + '">' + element.name + '</option>');
        });

        $("#search_servers option").remove();
        $("#search_servers").append(items.join(""));
    }
    function getAppsList(data) {
        data = data.applications.sort(sortAppsName);
        var items = [];
        $.each(data, function (key, element) {
            items.push('<option value="' + element + '">' + element + '</option>');
        });

        $("#search_apps").append(items.join(""));
    }
    function getCriteriaList(data) {
        var items = [];
        $.each(data, function (index, val) {
            items.push('<option value="' + val + '">' + val + '</option>');
        });

        $(".searchCriteriaSelect").append(items.join(""));
    }
    function validate(searchValues) {
        var isValid = true;
        if (!$('#search_servers_to option').length) {
            isValid = false;
        }
        if ($('#search_apps_to option').length === 0) {
            isValid = false;
        }
        if (!$('.date input').val()) {
            isValid = false;
        }
        if (searchValues.length === 0) {
            isValid = false;
        }

        if (!isValid) {
            $('#errorBlock').html('<p class="error" >Please Fill required data</p>');
            return false;
        } else {
            $('#errorBlock p').remove();
            return true;
        }
    }
    function validateIp(ip, serverName) {
        if (/(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)/
                .test(ip)) {
            return serverName.length;
        } else {
            return false;
        }

    }

    $.getJSON("/fileparser/servers", getServersList);
    $.getJSON("/fileparser/app", getAppsList);
    $.getJSON("/fileparser/searchcriteria", getCriteriaList);

    $('.searchCriteriaBlock select').on('change', function () {
        if (this.value === 'ICM Info') {
            $('.addSearchSection').attr("disabled", 'disabled');
        } else {
            $('.addSearchSection').attr("disabled", false);
            $(this).find('option[value="ICM Info"]').attr("disabled", 'disabled')
        }
    });

    $('#search_apps').multiselect({
        search: {
            left: '<input type="text" name="q" class="form-control" placeholder="Search..." />',
            right: '<input type="text" name="q" class="form-control" placeholder="Search..." />',
        },
        fireSearch: function (value) {
            return value.length > 3;
        }
    });

    $('#search_servers').multiselect({
        search: {
            left: '<input type="text" name="q" class="form-control" placeholder="Search..." />',
            right: '<input type="text" name="q" class="form-control" placeholder="Search..." />',
        },
        fireSearch: function (value) {
            return value.length > 3;
        }
    });

    $(".addSearchSection").on('click', function () {
        var content = '<div class="row searchCriteriaBlock">' + $(".searchCriteriaBlock").html() +
            '<div class="col-xs-1"><div><button class="btn btn-block btn-sm glyphicon glyphicon-trash delete"></button></div>'
            + '</div>';
        $("#searchArea").append(content);
    });

    $("body").on("click", ".delete", function () {
        if ($('.delete').length === 1) {
            $('.searchCriteriaBlock select').find('option[value="ICM Info"]').attr("disabled", false)
        }
        $(this).parent().parent().parent().remove();
    });

    $("#searchButton").on('click', function () {
        // clean output
        $('#easyPaginate').children('li').remove();
        $('.easyPaginateNav').remove();
        $(".totalRecords").text('');

        var servers = [];
        var apps = [];
        var searchOption = [];
        var searchValue = [];
        var date = [];

        $('.searchCriteriaBlock option').each(function () {
            if ($(this).is(':selected')) {
                searchOption.push($(this).val());
            }
        });
        searchOption.pop(); // delete last option and or OR

        $('.searchCriteriaBlock input').each(function () {
            if ($(this).val().length > 0) {
                searchValue.push($(this).val());
            }
        });

        if (!validate(searchValue)) {
            return false;
        }

        // gets selected servers
        $("#search_servers_to option").each(function () {
            servers.push($(this).val());
        });

        // gets selected apps
        $("#search_apps_to option").each(function () {
            apps.push($(this).val());
        });

        var payload = {};
        payload.servers = servers;
        payload.apps = apps;
        payload.searchOption = searchOption;
        payload.searchValue = searchValue;
        payload.date = [$('.date input').val()];

        $.ajax({
            timeout: 120000, // 2 min
            type: "POST",
            url: "/fileparser/search",
            dataType: "json", // expected format for response
            contentType: "application/json", // send as JSON
            data: JSON.stringify(payload)
        }).done(function (data) {
            var records = 0;
            Object.keys(data).forEach(function (key) {
                if (data[key]) {
                    records++;
                    $("#easyPaginate").append('<li><div class="col-xs-12 module-separator-sm">' +
                        '<div class="col-xs-6 appName">' + key + '</div>' +
                        '<div class="col-xs-6" align="right">' + '</div></div>' +
                        '<div class="col-xs-12">' +
                        '<div class="output">' + data[key] + '</div></div></li>');
                }
            });

            $(".totalRecords").text(records + (records > 1 ? ' records..' : ' record..'));

            $('#easyPaginate').easyPaginate({
                paginateElement: 'li',
                elementsPerPage: 2,
                prevButton: false,
                nextButton: false
            });
        });
    });

    $("#cancel").on('click', function () {
        $(".alert").addClass("hidden").removeClass("alert-success alert-danger");
    });

    $('#addServerModal').on('shown.bs.modal', function () {
        $(".alert").addClass("hidden").removeClass("alert-success alert-danger");
        $('#idAddress').focus();
    });

    $(".addServerModal").on('click', function () {

        $(".alert").removeClass("alert-success alert-danger");

        var ip = $('#idAddress').val();
        var serverName = $('#displayName').val();
        // validation ip
        if (!validateIp(ip, serverName)) {
            $(".alert").addClass("alert-danger").removeClass("hidden").text("Input is invalid.  Please, fill in IP and Name fields.");
            return false;
        }
        var payload = {};
        payload.name = serverName;
        payload.ip = ip;

        $.ajax({
            timeout: 120000, // 2 min
            type: "POST",
            url: "/fileparser/newServer",
            dataType: "json", // expected format for response
            contentType: "application/json", // send as JSON
            data: JSON.stringify(payload)
        }).done(function (data) {
            $(".alert").addClass("alert-success").removeClass("hidden").text("Server has been added.");
            $.getJSON("/fileparser/servers", getServersList);

        }).fail(function (data) {
            console.log(data.responseJSON)
            console.log(data.responseJSON.name)
            $(".alert").addClass("alert-danger").removeClass("hidden").text("Server already exists.")

        });

    })


});