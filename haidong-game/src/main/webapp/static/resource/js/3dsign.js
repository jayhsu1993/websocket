(function(d, c, a) {
	function b(f, g, h) {
        this.container = f;
        this.stageContainer = f.find("#surface");
        this.cardWidth = 60;
        this.cardHeight = 60;
        this.cardCount = 100;
        this.width = 0;
        this.height = 0;
        this.players = g;
        this.newJoinPlayer = [];
        this.is_init = false;
        this.aniRun = false;
        this.cards = [];
        this.emptyCards = [];
        this.card_index = 0;
        this.newPlayerHtml = null;
        this.map = ["11111111111111", "11111111111111", "11111111111111", "11111111111111", "11111111111111", "11111111111111", "11111111111111"];
        if (h) {
            this.map = h
        }
        this.wCount = this.map[0].length;
        this.hCount = this.map.length;
        this.currentType = 1;
        this.textType = {
        		0:[],
        		1:["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "15", "16", "19", "22", "23", "25", "26", "29", "43", "57", "71", "85", "86", "87", "88", "73", "72", "58", "37", "51", "65", "79", "93", "92", "91", "90", "77", "78", "64", "39", "53", "67", "40", "54", "68", "81", "82","94","97"],
        		2:["1", "15", "29", "43", "57", "71", "72", "58", "44", "30", "16", "2", "3", "5", "87", "89", "18", "32", "46", "60", "74", "6", "20", "34", "48", "62", "76", "90", "91", "93", "94", "8", "22", "36", "50", "64", "78", "10", "24", "38", "52", "66", "80", "26", "27", "41", "40", "68", "69", "83", "82"]
        }
    
    }
	var e = b.prototype;
    e.init = function() {
        this.wCount = this.map[0].length;
        this.hCount = this.map.length;
        this.map = this.mapTransformArray(this.map);
        this.cardCount = this.map.length;
        this.newPlayerHtml = this.createBigCard();
        this.initCards(this.players);
        this.updateSizes();
        this.resetCardIndex();
        this.random_positions()
    };
    e.createBigCard = function() {
//      var j = d("<div><img><span></span></div>");
//      j.addClass("bigCard");
//      var g = this.container.width();
//      var i = this.container.height();
//      var k = 467;
//      var h = 588;
//      var f = (g - k) / 2;
//      var l = (i - h) / 2;
//      j.css({
//          x: f,
//          y: l,
//          width: k,
//          height: h
//      });
//      this.container.append(j);
//      return j
    };
    e.random_positions = function() {
        var i = 600;
        var f = this;
        var h = this.width / 2;
        var g = this.height / 2;
        snabbt(f.cards, {
            fromPosition: function() {
                return [h + i - 2 * i * Math.random(), g + i - 2 * i * Math.random(), i - 2 * i * Math.random()]
            },
            position: function(j) {
                return [h + i - 2 * i * Math.random(), g + i - 2 * i * Math.random(), i - 2 * i * Math.random()]
            },
            fromOpacity: 0,
            opacity: 1,
            duration: 1000,
            easing: "ease",
            allDone: function() {
                f.tableFormation();
                setTimeout(function() {
                    f.rotate_container()
                },
                10000)
            }
        })
    };
    e.tableFormation = function() {
    	
        var k = this;
        var g = this.cardWidth + 10;
        var j = this.cardHeight + 10;
        var l = k.map;
        var i = (this.width - k.wCount * g) / 2;
        var f = (this.height - k.hCount * j) / 2;
        
        k.textType[k.currentType].forEach(function(item,index,array){
    		console.log(item);
    		$($('#surface .card')[item]).removeClass('fadeIn').addClass('fadeOut');
    	});
        
        k.currentType = k.currentType == 2?0:k.currentType +1;
                
        snabbt(k.cards, {
            rotation: [0, 0, 0],
            position: function(m) {
                var o = l[m],
                h = 0,
                n = 0;
                if (o) {
                    h = l[m].posX * g + i;
                    n = l[m].posY * j + f
                }
                return [h, n, 0]
            },
            easing: "spring",
            springConstant: 0.3,
            springDeceleration: 0.7,
            delay: elementDelay,
            allDone: function() {
                setTimeout(function() {
                    k.spiralFormation()
                },
                15000)
            }
        })
    };
    e.mapTransformArray = function(l) {
        var f = [];
        for (var k = 0; k < l.length; k++) {
            var g = l[k].split("");
            for (var h = 0; h < g.length; h++) {
                if (parseInt(g[h]) == 1) {
                    f.push({
                        posY: k,
                        posX: h
                    })
                }
            }
        }
        return f
    };
    e.cylinder = function() {
        var h = this;
        var j = this.width / 2;
        var i = this.height / 2;
        var f = 6;
        var g = 7;
        var k = h.cards.length / 2 * g;
        snabbt(h.cards, {
            position: function(o, n) {
                var m = Math.sin(f * 2 * Math.PI * o / n);
                var p = Math.cos(f * 2 * Math.PI * o / n);
                var l = 300;
                return [l * m + j, -k + o * g + i, l * p]
            },
            rotation: function(n, l) {
                var m = -(n / l) * f * Math.PI * 2;
                while (m < -2 * Math.PI) {
                    m += 2 * Math.PI
                }
                return [0, m, 0]
            },
            easing: "spring",
            springConstant: 0.3,
            springDeceleration: 0.7,
            delay: elementDelay,
            allDone: function() {}
        })
    };
    e.spiralFormation = function() {
    	$('#surface .card').removeClass('fadeOut').addClass('fadeIn');
        var h = this;
        var j = this.width / 2;
        var i = this.height / 2;
        var f = 6;
        var g = 7;
        var k = h.cards.length / 2 * g;
        snabbt(h.cards, {
            position: function(o, n) {
                var m = Math.sin(f * 2 * Math.PI * o / n);
                var p = Math.cos(f * 2 * Math.PI * o / n);
                var l = 300;
                return [l * m + j, -k + o * g + i, l * p]
            },
            rotation: function(n, l) {
                var m = -(n / l) * f * Math.PI * 2;
                while (m < -2 * Math.PI) {
                    m += 2 * Math.PI
                }
                return [0, m, 0]
            },
            easing: "spring",
            springConstant: 0.3,
            springDeceleration: 0.7,
            delay: elementDelay,
            allDone: function() {
                setTimeout(function() {
                    h.gridFormation()
                },
                5000)
            }
        })
    };
    e.gridFormation = function() {
    	
    	$('#surface .card').removeClass('fadeOut').addClass('fadeIn');
        var m = this;
        var k = this.width / 2;
        var i = this.height / 2;
        var l = 120;
        var j = 120;
        var o = 5;
        var g = 5 * 5;
        var f = -Math.floor(o / 2) * l;
        var n = -Math.floor(o / 2) * l;
        var h = Math.floor(5 / 2) * j;
        snabbt(m.cards, {
            rotation: [0, 0, 0],
            position: function(r) {
                var q = Math.floor(r / g);
                var t = r - q * g;
                var s = Math.floor(t / o);
                var p = t % o;
                return [f + p * l + k, n + s * l + i, h - q * j]
            },
            easing: "spring",
            springConstant: 0.3,
            springDeceleration: 0.7,
            delay: elementDelay,
            allDone: function() {
                setTimeout(function() {
                    m.tableFormation()
                },
                5000)
            }
        })
    };
    e.rotate_container = function() {
        var g = a.querySelector(".root");
        var f = a.querySelector(".signin3D");
        setupCameraControls(f, g)
    };
    e.addPlayer = function(f) {
        if (!this.isHavePlayer(f)) {
            if (this.is_init == true) {
                this.newJoinPlayer.push(f);
                if (this.aniRun == false) {
                    this.aniRun = true;
                    this.startAni()
                }
            } else {
                this.players.push(f)
            }
        }
    };
    e.startAni = function() {
        if (this.newJoinPlayer.length > 0) {
            var h = this;
            var g = h.newJoinPlayer.shift();
            var f = h.newPlayerHtml;
            f.find("img").attr("src", g.head_img);
            f.find("span").text(g.nickname);
            f.show();
            f.css({
                opacity: 0,
                scale: 0.1
            });
            h.players.push(g);
            f.transition({
                opacity: 1,
                scale: 1.1
            },
            500, "ease",
            function() {
                f.transition({
                    scale: 1
                },
                100, "ease",
                function() {
                    f.transition({
                        delay: 5000,
                        complete: function() {
                            h.toGrid(g, f)
                        }
                    })
                })
            })
        } else {
            this.aniRun = false
        }
    };
    e.toGrid = function(g, f) {
        var i = this.getBoxHtml();
        var h = this;
        f.transition({
            scale: 0.15,
            opacity: 0
        },
        1000, "ease",
        function() {
            i.find("img").fadeIn().attr("src", g.head_img);
            h.startAni()
        })
    };
    e.getBoxHtml = function() {
        var f = 0;
        if (this.emptyCards.length > 0) {
            f = Utility.getRandom(0, this.emptyCards.length);
            return this.emptyCards.splice(f, 1)[0]
        } else {
            f = Utility.getRandom(0, this.cardCount);
            return this.cardAt(f)
        }
    };
    e.isHavePlayer = function(g) {
        var f = Utility.array_search(this.newJoinPlayer, g.uid, "uid");
        if (f != null) {
            return true
        }
        f = Utility.array_search(this.players, g.uid, "uid");
        return f != null
    };
    e.updateSizes = function() {
        var g = this.container;
        this.width = g.width();
        this.height = g.height();
        for (var f = 0; f < this.cards.length; ++f) {
            this.cardAt(f).css("width", this.cardWidth + "px");
            this.cardAt(f).css("height", this.cardHeight + "px")
        }
    };
    e.show = function() {
        var f = this;
        f.container.fadeIn();
        d("body").css("background", "#000").find("#bg_ul").addClass("blur");
        if (f.is_init == false) {
            f.init();
            f.is_init = true
        }
    };
    e.hide = function() {
        this.container.fadeOut();
        d("body").css("background", "#37034E").find("#bg_ul").removeClass("blur")
    };
    e.showOrHide = function() {
        if (this.container.is(":visible")) {
            this.hide()
        } else {
            this.show()
        }
    };
    e.initCards = function(h) {
        var f = null,
        j;
        for (var g = 0; g < this.cardCount; g++) {
            j = this.createCard();
            this.cards.push(j);
            f = h[g];
            if (f != null) {
                this.renderCard(j, f)
            } else {
                this.emptyCards.push(d(j))
            }
        }
    };
    e.createCard = function() {
        var g = a.createElement("div");
        g.className = "card imgOut";
        var f = a.createElement("img");
        g.appendChild(f);
        this.stageContainer.append(d(g));
        return g
    };
    e.renderCard = function(g, f) {
        g = d(g);
        if (f.head_img != "") {
            g.find("img").attr("src", f.head_img)
        }
        g.removeClass("imgOut").addClass("imgIn")
    };
    e.nextCard = function() {
        if (this.card_index > this.cardCount) {
            return
        }
        return this.cards[this.card_index++]
    };
    e.cardAt = function(f) {
        return d(this.cards[f])
    };
    e.resetCardIndex = function() {
        this.card_index = 0
    };
    c.SingninCls = b
} (jQuery, window, document));
function elementDelay(a) {
    return a * 10
}
function setupCameraControls(c, j) {
    var b = 0;
    var i = 10;
    var h = 10;
    var f = -54;
    var d = 0.4;
    var e = 1;
    var k = 0;
    var g = window.innerWidth < 480 ? 800 : 0;
    j.style.transform = "perspective(1000px) rotateY(0deg) rotateX(0deg)";
    j.style.webkitTransform = "perspective(1000px) rotateY(0deg) rotateX(0deg)";
    function a(p, o, m) {
        var n = "perspective(1000px) translateZ(" + (m - g) + "px) rotateY(" + p + "deg) rotateX(" + -o + "deg)";
        j.style.transform = n;
        j.style.webkitTransform = n
    }
    function l() {
        i += e;
        h += k;
        var n = i + f;
        var m = h + d;
        a(n, m, b);
        window.requestAnimationFrame(l)
    }
    window.requestAnimationFrame(l)
};



