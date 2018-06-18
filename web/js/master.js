$('.menu a[data-menu]').on('click', function () {
                var menu = $(this).data('menu');
                $('.menu a.active').removeClass('active');
                $(this).addClass('active');
                $('.active[data-page]').removeClass('active');
                $('[data-page="' + menu + '"]').addClass('active');
            });

