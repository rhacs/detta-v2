// Esperar a que termine de cargar la página
$(function() {
    // Gráfico para los Accidentes por Mes
    new Chart($('#aPerMonth'), {
        type: 'line',
        data: {
            labels: perMonthLabels,
            datasets: [{
                borderColor: 'rgba(0, 75, 160, 0.75)',
                backgroundColor: 'rgba(0, 75, 160, 0.45)',
                label: accidentsLabel,
                data: perMonthValues,
                borderWidth: 2
            }]
        },
        options: {
            maintainAspectRatio: false,
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

    // Gráfico para los Accidentes por Tipo
    new Chart($('#aPerType'), {
        type: 'pie',
        data: {
            labels: perTypeLabels,
            datasets: [{
                backgroundColor: ['rgba(0, 75, 160, 0.5)', 'rgba(0, 75, 160, 0.75)'],
                label: accidentsLabel,
                data: perTypeValues
            }]
        },
        options: {
            maintainAspectRatio: false,
        }
    });

    // Gráfico para los Accidentes por Clasificación
    new Chart($('#aPerClass'), {
        type: 'bar',
        data: {
            labels: perClassLabels,
            datasets: [{
                borderColor: 'rgba(0, 75, 160, 0.75)',
                backgroundColor: 'rgba(0, 75, 160, 0.5)',
                label: accidentsLabel,
                data: perClassValues,
                borderWidth: 2
            }]
        },
        options: {
            maintainAspectRatio: false,
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

});
