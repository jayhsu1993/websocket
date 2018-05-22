// VERSION: 2.3 LAST UPDATE: 11.07.2013
/* 
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 * 
 * Made by Wilq32, wilq32@gmail.com, Wroclaw, Poland, 01.2009
 * Website: http://code.google.com/p/jqueryrotate/ 
 */

(function($) {
	var supportedCSS, supportedCSSOrigin, styles = document.getElementsByTagName("head")[0].style,
		toCheck = "transformProperty WebkitTransform OTransform msTransform MozTransform".split(" ");
	for(var a = 0; a < toCheck.length; a++)
		if(styles[toCheck[a]] !== undefined) {
			supportedCSS = toCheck[a];
		}
	if(supportedCSS) {
		supportedCSSOrigin = supportedCSS.replace(/[tT]ransform/, "TransformOrigin");
		if(supportedCSSOrigin[0] == "T") supportedCSSOrigin[0] = "t";
	}

	// Bad eval to preven google closure to remove it from code o_O
	eval('IE = "v"=="\v"');

	jQuery.fn.extend({
		rotate: function(parameters) {
			if(this.length === 0 || typeof parameters == "undefined") return;
			if(typeof parameters == "number") parameters = {
				angle: parameters
			};
			var returned = [];
			for(var i = 0, i0 = this.length; i < i0; i++) {
				var element = this.get(i);
				if(!element.Wilq32 || !element.Wilq32.PhotoEffect) {

					var paramClone = $.extend(true, {}, parameters);
					var newRotObject = new Wilq32.PhotoEffect(element, paramClone)._rootObj;

					returned.push($(newRotObject));
				} else {
					element.Wilq32.PhotoEffect._handleRotation(parameters);
				}
			}
			return returned;
		},
		getRotateAngle: function() {
			var ret = [];
			for(var i = 0, i0 = this.length; i < i0; i++) {
				var element = this.get(i);
				if(element.Wilq32 && element.Wilq32.PhotoEffect) {
					ret[i] = element.Wilq32.PhotoEffect._angle;
				}
			}
			return ret;
		},
		stopRotate: function() {
			for(var i = 0, i0 = this.length; i < i0; i++) {
				var element = this.get(i);
				if(element.Wilq32 && element.Wilq32.PhotoEffect) {
					clearTimeout(element.Wilq32.PhotoEffect._timer);
				}
			}
		}
	});

	// Library agnostic interface

	Wilq32 = window.Wilq32 || {};
	Wilq32.PhotoEffect = (function() {

		if(supportedCSS) {
			return function(img, parameters) {
				img.Wilq32 = {
					PhotoEffect: this
				};

				this._img = this._rootObj = this._eventObj = img;
				this._handleRotation(parameters);
			}
		} else {
			return function(img, parameters) {
				this._img = img;
				this._onLoadDelegate = [parameters];

				this._rootObj = document.createElement('span');
				this._rootObj.style.display = "inline-block";
				this._rootObj.Wilq32 = {
					PhotoEffect: this
				};
				img.parentNode.insertBefore(this._rootObj, img);

				if(img.complete) {
					this._Loader();
				} else {
					var self = this;
					// TODO: Remove jQuery dependency
					jQuery(this._img).bind("load", function() {
						self._Loader();
					});
				}
			}
		}
	})();

	Wilq32.PhotoEffect.prototype = {
		_setupParameters: function(parameters) {
			this._parameters = this._parameters || {};
			if(typeof this._angle !== "number") {
				this._angle = 0;
			}
			if(typeof parameters.angle === "number") {
				this._angle = parameters.angle;
			}
			this._parameters.animateTo = (typeof parameters.animateTo === "number") ? (parameters.animateTo) : (this._angle);

			this._parameters.step = parameters.step || this._parameters.step || null;
			this._parameters.easing = parameters.easing || this._parameters.easing || this._defaultEasing;
			this._parameters.duration = parameters.duration || this._parameters.duration || 1000;
			this._parameters.callback = parameters.callback || this._parameters.callback || this._emptyFunction;
			this._parameters.center = parameters.center || this._parameters.center || ["50%", "50%"];
			if(typeof this._parameters.center[0] == "string") {
				this._rotationCenterX = (parseInt(this._parameters.center[0], 10) / 100) * this._imgWidth * this._aspectW;
			} else {
				this._rotationCenterX = this._parameters.center[0];
			}
			if(typeof this._parameters.center[1] == "string") {
				this._rotationCenterY = (parseInt(this._parameters.center[1], 10) / 100) * this._imgHeight * this._aspectH;
			} else {
				this._rotationCenterY = this._parameters.center[1];
			}

			if(parameters.bind && parameters.bind != this._parameters.bind) {
				this._BindEvents(parameters.bind);
			}
		},
		_emptyFunction: function() {},
		_defaultEasing: function(x, t, b, c, d) {
			return -c * ((t = t / d - 1) * t * t * t - 1) + b
		},
		_handleRotation: function(parameters, dontcheck) {
			if(!supportedCSS && !this._img.complete && !dontcheck) {
				this._onLoadDelegate.push(parameters);
				return;
			}
			this._setupParameters(parameters);
			if(this._angle == this._parameters.animateTo) {
				this._rotate(this._angle);
			} else {
				this._animateStart();
			}
		},

		_BindEvents: function(events) {
			if(events && this._eventObj) {
				// Unbinding previous Events
				if(this._parameters.bind) {
					var oldEvents = this._parameters.bind;
					for(var a in oldEvents)
						if(oldEvents.hasOwnProperty(a))
						// TODO: Remove jQuery dependency
							jQuery(this._eventObj).unbind(a, oldEvents[a]);
				}

				this._parameters.bind = events;
				for(var a in events)
					if(events.hasOwnProperty(a))
					// TODO: Remove jQuery dependency
						jQuery(this._eventObj).bind(a, events[a]);
			}
		},

		_Loader: (function() {
			if(IE)
				return function() {
					var width = this._img.width;
					var height = this._img.height;
					this._imgWidth = width;
					this._imgHeight = height;
					this._img.parentNode.removeChild(this._img);

					this._vimage = this.createVMLNode('image');
					this._vimage.src = this._img.src;
					this._vimage.style.height = height + "px";
					this._vimage.style.width = width + "px";
					this._vimage.style.position = "absolute"; // FIXES IE PROBLEM - its only rendered if its on absolute position!
					this._vimage.style.top = "0px";
					this._vimage.style.left = "0px";
					this._aspectW = this._aspectH = 1;

					/* Group minifying a small 1px precision problem when rotating object */
					this._container = this.createVMLNode('group');
					this._container.style.width = width;
					this._container.style.height = height;
					this._container.style.position = "absolute";
					this._container.style.top = "0px";
					this._container.style.left = "0px";
					this._container.setAttribute('coordsize', width - 1 + ',' + (height - 1)); // This -1, -1 trying to fix ugly problem with small displacement on IE
					this._container.appendChild(this._vimage);

					this._rootObj.appendChild(this._container);
					this._rootObj.style.position = "relative"; // FIXES IE PROBLEM
					this._rootObj.style.width = width + "px";
					this._rootObj.style.height = height + "px";
					this._rootObj.setAttribute('id', this._img.getAttribute('id'));
					this._rootObj.className = this._img.className;
					this._eventObj = this._rootObj;
					var parameters;
					while(parameters = this._onLoadDelegate.shift()) {
						this._handleRotation(parameters, true);
					}
				}
			else return function() {
				this._rootObj.setAttribute('id', this._img.getAttribute('id'));
				this._rootObj.className = this._img.className;

				this._imgWidth = this._img.naturalWidth;
				this._imgHeight = this._img.naturalHeight;
				var _widthMax = Math.sqrt((this._imgHeight) * (this._imgHeight) + (this._imgWidth) * (this._imgWidth));
				this._width = _widthMax * 3;
				this._height = _widthMax * 3;

				this._aspectW = this._img.offsetWidth / this._img.naturalWidth;
				this._aspectH = this._img.offsetHeight / this._img.naturalHeight;

				this._img.parentNode.removeChild(this._img);

				this._canvas = document.createElement('canvas');
				this._canvas.setAttribute('width', this._width);
				this._canvas.style.position = "relative";
				this._canvas.style.left = -this._img.height * this._aspectW + "px";
				this._canvas.style.top = -this._img.width * this._aspectH + "px";
				this._canvas.Wilq32 = this._rootObj.Wilq32;

				this._rootObj.appendChild(this._canvas);
				this._rootObj.style.width = this._img.width * this._aspectW + "px";
				this._rootObj.style.height = this._img.height * this._aspectH + "px";
				this._eventObj = this._canvas;

				this._cnv = this._canvas.getContext('2d');
				var parameters;
				while(parameters = this._onLoadDelegate.shift()) {
					this._handleRotation(parameters, true);
				}
			}
		})(),

		_animateStart: function() {
			if(this._timer) {
				clearTimeout(this._timer);
			}
			this._animateStartTime = +new Date;
			this._animateStartAngle = this._angle;
			this._animate();
		},
		_animate: function() {
			var actualTime = +new Date;
			var checkEnd = actualTime - this._animateStartTime > this._parameters.duration;

			// TODO: Bug for animatedGif for static rotation ? (to test)
			if(checkEnd && !this._parameters.animatedGif) {
				clearTimeout(this._timer);
			} else {
				if(this._canvas || this._vimage || this._img) {
					var angle = this._parameters.easing(0, actualTime - this._animateStartTime, this._animateStartAngle, this._parameters.animateTo - this._animateStartAngle, this._parameters.duration);
					this._rotate((~~(angle * 10)) / 10);
				}
				if(this._parameters.step) {
					this._parameters.step(this._angle);
				}
				var self = this;
				this._timer = setTimeout(function() {
					self._animate.call(self);
				}, 10);
			}

			// To fix Bug that prevents using recursive function in callback I moved this function to back
			if(this._parameters.callback && checkEnd) {
				this._angle = this._parameters.animateTo;
				this._rotate(this._angle);
				this._parameters.callback.call(this._rootObj);
			}
		},

		_rotate: (function() {
			var rad = Math.PI / 180;
			if(IE)
				return function(angle) {
					this._angle = angle;
					this._container.style.rotation = (angle % 360) + "deg";
					this._vimage.style.top = -(this._rotationCenterY - this._imgHeight / 2) + "px";
					this._vimage.style.left = -(this._rotationCenterX - this._imgWidth / 2) + "px";
					this._container.style.top = this._rotationCenterY - this._imgHeight / 2 + "px";
					this._container.style.left = this._rotationCenterX - this._imgWidth / 2 + "px";

				}
			else if(supportedCSS)
				return function(angle) {
					this._angle = angle;
					this._img.style[supportedCSS] = "rotate(" + (angle % 360) + "deg)";
					this._img.style[supportedCSSOrigin] = this._parameters.center.join(" ");
				}
			else
				return function(angle) {
					this._angle = angle;
					angle = (angle % 360) * rad;
					// clear canvas	
					this._canvas.width = this._width; //+this._widthAdd;
					this._canvas.height = this._height; //+this._heightAdd;

					// REMEMBER: all drawings are read from backwards.. so first function is translate, then rotate, then translate, translate..
					this._cnv.translate(this._imgWidth * this._aspectW, this._imgHeight * this._aspectH); // at least center image on screen
					this._cnv.translate(this._rotationCenterX, this._rotationCenterY); // we move image back to its orginal 
					this._cnv.rotate(angle); // rotate image
					this._cnv.translate(-this._rotationCenterX, -this._rotationCenterY); // move image to its center, so we can rotate around its center
					this._cnv.scale(this._aspectW, this._aspectH); // SCALE - if needed ;)
					this._cnv.drawImage(this._img, 0, 0); // First - we draw image
				}

		})()
	}

	if(IE) {
		Wilq32.PhotoEffect.prototype.createVMLNode = (function() {
			document.createStyleSheet().addRule(".rvml", "behavior:url(#default#VML)");
			try {
				!document.namespaces.rvml && document.namespaces.add("rvml", "urn:schemas-microsoft-com:vml");
				return function(tagName) {
					return document.createElement('<rvml:' + tagName + ' class="rvml">');
				};
			} catch(e) {
				return function(tagName) {
					return document.createElement('<' + tagName + ' xmlns="urn:schemas-microsoft.com:vml" class="rvml">');
				};
			}
		})();
	}

})(jQuery);/* observejs --- By dnt http://kmdjs.github.io/
 * Github: https://github.com/kmdjs/observejs
 * MIT Licensed.
 */
; (function (win) {
    var observe = function (target, arr, callback) {
        var _observe = function (target, arr, callback) {
            if (!target.$observer) target.$observer = this;
            var $observer = target.$observer;
            var eventPropArr = [];
            if (observe.isArray(target)) {
                if (target.length === 0) {
                    target.$observeProps = {};
                    target.$observeProps.$observerPath = "#";
                }
                $observer.mock(target);

            }
            for (var prop in target) {
                if (target.hasOwnProperty(prop)) {
                    if (callback) {
                        if (observe.isArray(arr) && observe.isInArray(arr, prop)) {
                            eventPropArr.push(prop);
                            $observer.watch(target, prop);
                        } else if (observe.isString(arr) && prop == arr) {
                            eventPropArr.push(prop);
                            $observer.watch(target, prop);
                        }
                    } else {
                        eventPropArr.push(prop);
                        $observer.watch(target, prop);
                    }
                }
            }
            $observer.target = target;
            if (!$observer.propertyChangedHandler) $observer.propertyChangedHandler = [];
            var propChanged = callback ? callback : arr;
            $observer.propertyChangedHandler.push({ all: !callback, propChanged: propChanged, eventPropArr: eventPropArr });
        }
        _observe.prototype = {
            "onPropertyChanged": function (prop, value, oldValue, target, path) {
                if (value !== oldValue && this.propertyChangedHandler) {
                    var rootName = observe._getRootName(prop, path);
                    for (var i = 0, len = this.propertyChangedHandler.length; i < len; i++) {
                        var handler = this.propertyChangedHandler[i];
                        if (handler.all || observe.isInArray(handler.eventPropArr, rootName) || rootName.indexOf("Array-") === 0) {
                            handler.propChanged.call(this.target, prop, value, oldValue, path);
                        }
                    }
                }
                if (prop.indexOf("Array-") !== 0 && typeof value === "object") {
                    this.watch(target, prop, target.$observeProps.$observerPath);
                }
            },
            "mock": function (target) {
                var self = this;
                observe.methods.forEach(function (item) {
                    target[item] = function () {
                        var old = Array.prototype.slice.call(this, 0);
                        var result = Array.prototype[item].apply(this, Array.prototype.slice.call(arguments));
                        if (new RegExp("\\b" + item + "\\b").test(observe.triggerStr)) {
                            for (var cprop in this) {
                                if (this.hasOwnProperty(cprop) && !observe.isFunction(this[cprop])) {
                                    self.watch(this, cprop, this.$observeProps.$observerPath);
                                }
                            }
                            //todo
                            self.onPropertyChanged("Array-" + item, this, old, this, this.$observeProps.$observerPath);
                        }
                        return result;
                    };
                    target['real'+item.substring(0,1).toUpperCase()+item.substring(1)] = function () {
                        return Array.prototype[item].apply(this, Array.prototype.slice.call(arguments));
                    };
                });
            },
            "watch": function (target, prop, path) {
                if (prop === "$observeProps" || prop === "$observer") return;
                if (observe.isFunction(target[prop])) return;
                if (!target.$observeProps) target.$observeProps = {};
                if (path !== undefined) {
                    target.$observeProps.$observerPath = path;
                } else {
                    target.$observeProps.$observerPath = "#";
                }
                var self = this;
                var currentValue = target.$observeProps[prop] = target[prop];
                Object.defineProperty(target, prop, {
                    get: function () {
                        return this.$observeProps[prop];
                    },
                    set: function (value) {
                        var old = this.$observeProps[prop];
                        this.$observeProps[prop] = value;
                        self.onPropertyChanged(prop, value, old, this, target.$observeProps.$observerPath);
                    }
                });
                if (typeof currentValue == "object") {
                    if (observe.isArray(currentValue)) {
                        this.mock(currentValue);
                        if (currentValue.length === 0) {
                            if (!currentValue.$observeProps) currentValue.$observeProps = {};
                            if (path !== undefined) {
                                currentValue.$observeProps.$observerPath = path;
                            } else {
                                currentValue.$observeProps.$observerPath = "#";
                            }
                        }
                    }
                    for (var cprop in currentValue) {
                        if (currentValue.hasOwnProperty(cprop)) {
                            this.watch(currentValue, cprop, target.$observeProps.$observerPath + "-" + prop);
                        }
                    }
                }
            }
        }
        return new _observe(target, arr, callback)
    }
    observe.methods = ["concat", "copyWithin", "entries", "every", "fill", "filter", "find", "findIndex", "forEach", "includes", "indexOf", "join", "keys", "lastIndexOf", "map", "pop", "push", "reduce", "reduceRight", "reverse", "shift", "slice", "some", "sort", "splice", "toLocaleString", "toString", "unshift", "values", "size"]
    observe.triggerStr = ["concat", "copyWithin", "fill", "pop", "push", "reverse", "shift", "sort", "splice", "unshift", "size"].join(",")
    observe.isArray = function (obj) {
        return Object.prototype.toString.call(obj) === '[object Array]';
    }
    observe.isString = function (obj) {
        return typeof obj === "string";
    }
    observe.isInArray = function (arr, item) {
        for (var i = arr.length; --i > -1;) {
            if (item === arr[i]) return true;
        }
        return false;
    }
    observe.isFunction = function (obj) {
        return Object.prototype.toString.call(obj) == '[object Function]';
    }
    observe._getRootName = function (prop, path) {
        if (path === "#") {
            return prop;
        }
        return path.split("-")[1];
    }

    observe.add = function (obj, prop) {
        var $observer = obj.$observer;
        $observer.watch(obj, prop);
    }
    
    observe.set = function(obj, prop,value,exec) { 
        if(!exec){
            obj[prop] = value; 
        }
        var $observer = obj.$observer; 
        $observer.watch(obj, prop); 
        if(exec){
           obj[prop] = value;
        }
    }
    
    Array.prototype.size = function (length) {
        this.length = length;
    }

    if (typeof module != 'undefined' && module.exports && this.module !== module) { module.exports = observe }
    else if (typeof define === 'function' && define.amd) { define(observe) }
    else { win.observe = observe };
})(Function('return this')());
// MIT License:
//
// Copyright (c) 2010-2012, Joe Walnes
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

/**
 * This behaves like a WebSocket in every way, except if it fails to connect,
 * or it gets disconnected, it will repeatedly poll until it successfully connects
 * again.
 *
 * It is API compatible, so when you have:
 *   ws = new WebSocket('ws://....');
 * you can replace with:
 *   ws = new ReconnectingWebSocket('ws://....');
 *
 * The event stream will typically look like:
 *  onconnecting
 *  onopen
 *  onmessage
 *  onmessage
 *  onclose // lost connection
 *  onconnecting
 *  onopen  // sometime later...
 *  onmessage
 *  onmessage
 *  etc...
 *
 * It is API compatible with the standard WebSocket API, apart from the following members:
 *
 * - `bufferedAmount`
 * - `extensions`
 * - `binaryType`
 *
 * Latest version: https://github.com/joewalnes/reconnecting-websocket/
 * - Joe Walnes
 *
 * Syntax
 * ======
 * var socket = new ReconnectingWebSocket(url, protocols, options);
 *
 * Parameters
 * ==========
 * url - The url you are connecting to.
 * protocols - Optional string or array of protocols.
 * options - See below
 *
 * Options
 * =======
 * Options can either be passed upon instantiation or set after instantiation:
 *
 * var socket = new ReconnectingWebSocket(url, null, { debug: true, reconnectInterval: 4000 });
 *
 * or
 *
 * var socket = new ReconnectingWebSocket(url);
 * socket.debug = true;
 * socket.reconnectInterval = 4000;
 *
 * debug
 * - Whether this instance should log debug messages. Accepts true or false. Default: false.
 *
 * automaticOpen
 * - Whether or not the websocket should attempt to connect immediately upon instantiation. The socket can be manually opened or closed at any time using ws.open() and ws.close().
 *
 * reconnectInterval
 * - The number of milliseconds to delay before attempting to reconnect. Accepts integer. Default: 1000.
 *
 * maxReconnectInterval
 * - The maximum number of milliseconds to delay a reconnection attempt. Accepts integer. Default: 30000.
 *
 * reconnectDecay
 * - The rate of increase of the reconnect delay. Allows reconnect attempts to back off when problems persist. Accepts integer or float. Default: 1.5.
 *
 * timeoutInterval
 * - The maximum time in milliseconds to wait for a connection to succeed before closing and retrying. Accepts integer. Default: 2000.
 *
 */
(function(global, factory) {
	if(typeof define === 'function' && define.amd) {
		define([], factory);
	} else if(typeof module !== 'undefined' && module.exports) {
		module.exports = factory();
	} else {
		global.ReconnectingWebSocket = factory();
	}
})(this, function() {

	if(!('WebSocket' in window)) {
		return;
	}

	function ReconnectingWebSocket(url, protocols, options) {

		// Default settings
		var settings = {

			/** Whether this instance should log debug messages. */
			debug: false,

			/** Whether or not the websocket should attempt to connect immediately upon instantiation. */
			automaticOpen: true,

			/** The number of milliseconds to delay before attempting to reconnect. */
			reconnectInterval: 1000,
			/** The maximum number of milliseconds to delay a reconnection attempt. */
			maxReconnectInterval: 30000,
			/** The rate of increase of the reconnect delay. Allows reconnect attempts to back off when problems persist. */
			reconnectDecay: 1.5,

			/** The maximum time in milliseconds to wait for a connection to succeed before closing and retrying. */
			timeoutInterval: 2000,

			/** The maximum number of reconnection attempts to make. Unlimited if null. */
			maxReconnectAttempts: null,

			/** The binary type, possible values 'blob' or 'arraybuffer', default 'blob'. */
			binaryType: 'blob'
		}
		if(!options) {
			options = {};
		}

		// Overwrite and define settings with options if they exist.
		for(var key in settings) {
			if(typeof options[key] !== 'undefined') {
				this[key] = options[key];
			} else {
				this[key] = settings[key];
			}
		}

		// These should be treated as read-only properties

		/** The URL as resolved by the constructor. This is always an absolute URL. Read only. */
		this.url = url;

		/** The number of attempted reconnects since starting, or the last successful connection. Read only. */
		this.reconnectAttempts = 0;

		/**
		 * The current state of the connection.
		 * Can be one of: WebSocket.CONNECTING, WebSocket.OPEN, WebSocket.CLOSING, WebSocket.CLOSED
		 * Read only.
		 */
		this.readyState = WebSocket.CONNECTING;

		/**
		 * A string indicating the name of the sub-protocol the server selected; this will be one of
		 * the strings specified in the protocols parameter when creating the WebSocket object.
		 * Read only.
		 */
		this.protocol = null;

		// Private state variables

		var self = this;
		var ws;
		var forcedClose = false;
		var timedOut = false;
		var eventTarget = document.createElement('div');

		// Wire up "on*" properties as event handlers

		eventTarget.addEventListener('open', function(event) {
			self.onopen(event);
		});
		eventTarget.addEventListener('close', function(event) {
			self.onclose(event);
		});
		eventTarget.addEventListener('connecting', function(event) {
			self.onconnecting(event);
		});
		eventTarget.addEventListener('message', function(event) {
			self.onmessage(event);
		});
		eventTarget.addEventListener('error', function(event) {
			self.onerror(event);
		});

		// Expose the API required by EventTarget

		this.addEventListener = eventTarget.addEventListener.bind(eventTarget);
		this.removeEventListener = eventTarget.removeEventListener.bind(eventTarget);
		this.dispatchEvent = eventTarget.dispatchEvent.bind(eventTarget);

		/**
		 * This function generates an event that is compatible with standard
		 * compliant browsers and IE9 - IE11
		 *
		 * This will prevent the error:
		 * Object doesn't support this action
		 *
		 * http://stackoverflow.com/questions/19345392/why-arent-my-parameters-getting-passed-through-to-a-dispatched-event/19345563#19345563
		 * @param s String The name that the event should use
		 * @param args Object an optional object that the event will use
		 */
		function generateEvent(s, args) {
			var evt = document.createEvent("CustomEvent");
			evt.initCustomEvent(s, false, false, args);
			return evt;
		};

		this.open = function(reconnectAttempt) {
			ws = new WebSocket(self.url, protocols || []);
			ws.binaryType = this.binaryType;

			if(reconnectAttempt) {
				if(this.maxReconnectAttempts && this.reconnectAttempts > this.maxReconnectAttempts) {
					return;
				}
			} else {
				eventTarget.dispatchEvent(generateEvent('connecting'));
				this.reconnectAttempts = 0;
			}

			if(self.debug || ReconnectingWebSocket.debugAll) {
				console.debug('ReconnectingWebSocket', 'attempt-connect', self.url);
			}

			var localWs = ws;
			var timeout = setTimeout(function() {
				if(self.debug || ReconnectingWebSocket.debugAll) {
					console.debug('ReconnectingWebSocket', 'connection-timeout', self.url);
				}
				timedOut = true;
				localWs.close();
				timedOut = false;
			}, self.timeoutInterval);

			ws.onopen = function(event) {
				clearTimeout(timeout);
				if(self.debug || ReconnectingWebSocket.debugAll) {
					console.debug('ReconnectingWebSocket', 'onopen', self.url);
				}
				self.protocol = ws.protocol;
				self.readyState = WebSocket.OPEN;
				self.reconnectAttempts = 0;
				var e = generateEvent('open');
				e.isReconnect = reconnectAttempt;
				reconnectAttempt = false;
				eventTarget.dispatchEvent(e);
			};

			ws.onclose = function(event) {
				clearTimeout(timeout);
				ws = null;
				if(forcedClose) {
					self.readyState = WebSocket.CLOSED;
					eventTarget.dispatchEvent(generateEvent('close'));
				} else {
					self.readyState = WebSocket.CONNECTING;
					var e = generateEvent('connecting');
					e.code = event.code;
					e.reason = event.reason;
					e.wasClean = event.wasClean;
					eventTarget.dispatchEvent(e);
					if(!reconnectAttempt && !timedOut) {
						if(self.debug || ReconnectingWebSocket.debugAll) {
							console.debug('ReconnectingWebSocket', 'onclose', self.url);
						}
						eventTarget.dispatchEvent(generateEvent('close'));
					}

					var timeout = self.reconnectInterval * Math.pow(self.reconnectDecay, self.reconnectAttempts);
					setTimeout(function() {
						self.reconnectAttempts++;
						self.open(true);
					}, timeout > self.maxReconnectInterval ? self.maxReconnectInterval : timeout);
				}
			};
			ws.onmessage = function(event) {
				if(self.debug || ReconnectingWebSocket.debugAll) {
					console.debug('ReconnectingWebSocket', 'onmessage', self.url, event.data);
				}
				var e = generateEvent('message');
				e.data = event.data;
				eventTarget.dispatchEvent(e);
			};
			ws.onerror = function(event) {
				if(self.debug || ReconnectingWebSocket.debugAll) {
					console.debug('ReconnectingWebSocket', 'onerror', self.url, event);
				}
				eventTarget.dispatchEvent(generateEvent('error'));
			};
		}

		// Whether or not to create a websocket upon instantiation
		if(this.automaticOpen == true) {
			this.open(false);
		}

		/**
		 * Transmits data to the server over the WebSocket connection.
		 *
		 * @param data a text string, ArrayBuffer or Blob to send to the server.
		 */
		this.send = function(data) {
			if(ws) {
				if(self.debug || ReconnectingWebSocket.debugAll) {
					console.debug('ReconnectingWebSocket', 'send', self.url, data);
				}
				return ws.send(data);
			} else {
				throw 'INVALID_STATE_ERR : Pausing to reconnect websocket';
			}
		};

		/**
		 * Closes the WebSocket connection or connection attempt, if any.
		 * If the connection is already CLOSED, this method does nothing.
		 */
		this.close = function(code, reason) {
			// Default CLOSE_NORMAL code
			if(typeof code == 'undefined') {
				code = 1000;
			}
			forcedClose = true;
			if(ws) {
				ws.close(code, reason);
			}
		};

		/**
		 * Additional public API method to refresh the connection if still open (close, re-open).
		 * For example, if the app suspects bad data / missed heart beats, it can try to refresh.
		 */
		this.refresh = function() {
			if(ws) {
				ws.close();
			}
		};
	}

	/**
	 * An event listener to be called when the WebSocket connection's readyState changes to OPEN;
	 * this indicates that the connection is ready to send and receive data.
	 */
	ReconnectingWebSocket.prototype.onopen = function(event) {};
	/** An event listener to be called when the WebSocket connection's readyState changes to CLOSED. */
	ReconnectingWebSocket.prototype.onclose = function(event) {};
	/** An event listener to be called when a connection begins being attempted. */
	ReconnectingWebSocket.prototype.onconnecting = function(event) {};
	/** An event listener to be called when a message is received from the server. */
	ReconnectingWebSocket.prototype.onmessage = function(event) {};
	/** An event listener to be called when an error occurs. */
	ReconnectingWebSocket.prototype.onerror = function(event) {};

	/**
	 * Whether all instances of ReconnectingWebSocket should log debug messages.
	 * Setting this to true is the equivalent of setting all instances of ReconnectingWebSocket.debug to true.
	 */
	ReconnectingWebSocket.debugAll = false;

	ReconnectingWebSocket.CONNECTING = WebSocket.CONNECTING;
	ReconnectingWebSocket.OPEN = WebSocket.OPEN;
	ReconnectingWebSocket.CLOSING = WebSocket.CLOSING;
	ReconnectingWebSocket.CLOSED = WebSocket.CLOSED;

	return ReconnectingWebSocket;
});//礼物效果

var huajiaGift = (function() {
	var c = {};

	var isEnd = true;

	c.start = function() {

		if(isEnd) {
			isEnd = false;
			startAni();
			// setFlowerDown();
			setTimeout(removeAni, 7000);
		}

	}

	function startAni() {
		document.querySelector(".huajia-ground").classList
			.add('huajia-ground-move');
		document.querySelector(".huajia-man").classList.add('huajia-man-move');
		document.querySelector(".huajia-heart").classList
			.add('huajia-heart-move');
	}

	function removeAni() {
		document.querySelector(".huajia-ground").classList
			.remove('huajia-ground-move');
		document.querySelector(".huajia-man").classList
			.remove('huajia-man-move');
		document.querySelector(".huajia-heart").classList
			.remove('huajia-heart-move');
		isEnd = true;
	}

	function randomNum(n, m) {
		var random = Math.floor(Math.random() * (m - n)) + n;
		// console.log("rewardNames[" + random + "]");
		return random;
	}
	return c;
})();

var sanshengGift = (function() {
	var c = {};

	var isEnd = true;

	c.start = function() {

		if(isEnd) {
			isEnd = false;
			startAni();
			setFlowerDown();
			setTimeout(removeAni, 7000);
		}

	}

	function startAni() {
		document.querySelector(".tree").classList.add('tree-move');
		document.querySelector(".san-people").classList.add('people-move');
		document.querySelector(".gift-sansheng .text").classList.add('text-move');
	}

	function removeAni() {
		document.querySelector(".tree").classList.remove('tree-move');
		document.querySelector(".san-people").classList.remove('people-move');
		document.querySelector(".gift-sansheng .text").classList.remove('text-move');
		isEnd = true;
	}

	function setFlowerDown() {
		document.querySelector(".flower").innerHTML = '';
		var arr = [];
		for(var i = 0; i < 30; i++) {
			var div = document.createElement("div");
			div.className = 'f';
			div.innerHTML = '<img src="' + pageInfo.path + '/static/resource/img/san/flower.png" class="r"/>';
			div.style.left = randomNum(0, 800) + 700 + 'px';
			div.style.top = '-100px';
			document.querySelector(".flower").appendChild(div);
			arr.push(div);
		}

		setTimeout(function() {
			for(var j = 0; j < arr.length; j++) {
				arr[j].children[0].style.zoom = randomNum(60, 100) / 100;
				arr[j].children[0].style.animationDelay = (randomNum(1, 10) / 10) + 's';
				arr[j].style.transition = 'ease-out,' + randomNum(3, 6) + 's';
				arr[j].style.transform = 'translate(-' + randomNum(0, 600) + 'px,1200px)';
				arr[j].style.transitionDelay = Math.round(Math.random() * 3) + 's';
			}

		}, 50)

	}

	function randomNum(n, m) {
		var random = Math.floor(Math.random() * (m - n)) + n;
		//	console.log("rewardNames[" + random + "]");
		return random;
	}
	return c;
})();

//猜明星

var guessStar = (function() {

	var starList = [
		'qiwei',
		'wangdongcheng',
		'xiena2',
		'sunhao',
		'xujiao',
		'zhangliang',
		'guotao',
		
		'liangjiahui',
		'zhouhaitao',
		'dazhangwei',
		'jialing',
		'quying',
		'wang',
		'qianfeng',
		'liuqingyun',
		'sunjian',
		'liuxiang',
		'zhoudongyu',

		'handou',
		'geyou',
		'jinzhengen',
		'huge',
		'wangzulan',
		'liudehua',
		'xuzhen',
		'luoyufeng',
		'huangxiaoming',
		'xiena',
		'sunhonglei',
		'shixiaolong',
		'halibote',
		'zhaobenshan',
		'chenguanxi'
	];
	
	//打乱顺序
	starList.sort(function(){return 0.5 - Math.random()})
	
	var chooseList = {
		'1': 'handou',
		'2': 'geyou',
		'3': 'jinzhengen',
		'4': 'huge',
		'5': 'wangzulan',
		'6': 'liudehua',
		'7': 'xuzhen',
		'8': 'luoyufeng',
		'9': 'huangxiaoming',
		'10': 'xiena',
		'11': 'sunhonglei',
		'12': 'shixiaolong',
		'13': 'halibote',
		'14': 'zhaobenshan',
		'15': 'chenguanxi',
	}
	
	var starStatus = false;

	//var startSize = 15;
	var startSize = starList.length;
	var next = false;

	var currentStarIndex = 1;
	var photoIndex = 1;

	$('.guess-continue').on('click', function(e) {
		//		layer.msg('continue');
		if(photoIndex == 7) {
			$('.guess-answer').click();
			return;
		}

		photoIndex++;

		//$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' + chooseList[currentStarIndex] + '/' + photoIndex + '.png');
	
		$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' +starList[ parseInt(currentStarIndex)-1  ]  + '/' + photoIndex + '.png');
	})

	$('.guess-answer').on('click', function(e) {
		//layer.msg('answer');
		photoIndex = 8;
		$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' + starList[ parseInt(currentStarIndex)-1  ] + '/' + photoIndex + '.png');

		$(this).hide();
		$('.guess-continue').hide();
		$('.guess-rechoose').show();
		next = true;
		starStatus = false;
	})

	$('#preStar').on('click', function(e) {
		//console.log("123");
		if(starStatus) {
			layer.msg('请先揭晓答案');
			return;
		}

		if(next) {
			$('.guess-rechoose').click();
		}

		if(currentStarIndex == 1) {
			layer.msg('没有更多了');
			return;
		}

		currentStarIndex--;
//		$('#starIndex').attr('src', pageInfo.path + '/static/resource/img/guess/' + currentStarIndex + '.png')
		$('#starIndex').html(currentStarIndex);
	})

	$('#nextStar').on('click', function(e) {
		if(starStatus) {
			layer.msg('请先揭晓答案');
			return;
		}

		if(next) {
			$('.guess-rechoose').click();
		}

		if(currentStarIndex == startSize) {
			layer.msg('没有更多了');
			return;
		}
		currentStarIndex++;
		//$('#starIndex').attr('src', pageInfo.path + '/static/resource/img/guess/' + currentStarIndex + '.png')
		$('#starIndex').html(currentStarIndex);
	})

	$('.guess-start').on('click', function(e) {
		$(this).hide();
		$('.guess-answer').show();
		$('.guess-continue').show();
		starStatus = true;
		//		$('#nextStar').addClass('disabled');
		//		$('#preStar').addClass('disabled');
		//$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' + chooseList[currentStarIndex] + '/' + photoIndex + '.png');
		$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' + starList[ parseInt(currentStarIndex)-1  ] + '/' + photoIndex + '.png');
	})

	$('.guess-rechoose').on('click', function() {
		photoIndex = 1;
		$('.star-warp img').attr('src', '');
		$(this).hide();
		starStatus = false;
		next = false;
		$('.guess-start').show();
	})

	setTimeout(function() {
		var audio = new Audio();
		audio.src = pageInfo.path + '/static/resource/soundef/guess.mp3';
		//载入图片
		for(var i = 1; i < starList.length; i++) {
//			var img1 = new Image();
//			img1.src = pageInfo.path + '/static/resource/img/guess/' + i + '.png';
			for(var j = 1; j < 9; j++) {
				var img = new Image();
				//	console.log(pageInfo.path+'/static/resource/img/guess/star/'+ chooseList[i]+'/'+j+'.png');
				img.src = pageInfo.path + '/static/resource/img/guess/star/' + starList[i] + '/' + j + '.png';
			}
		}
	}, 5000)

})();/**
 * 弹幕模块
 */
var danmuModule = {
	setting: {
		time: 15000, //弹幕飘动时间
		isOpen: true
	},

	newyearTpl: function(headimg, username, content) {
		_content = danmuModule.cutDanmu(content);
		return '<div style="display: inline-block;vertical-align: middle;position: relative;">' +
			'<img src="http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/newyearhead.png"/>' +
			'<img src="' + headimg + '" onerror="errorImg(this)" style="position: absolute;top: 8px;left: 8px;border-radius: 100px;height: 50px;width:50px">' +
			'</div>' +
			'<div style="border-radius: 26px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;border:2px solid #ffe400;background-color:rgba(211,16,16,0.85)">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;">' + content + '</span>' +
			'</div>' +
			'<img src ="http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/newyear.png" style="position:absolute;top: -15px;left: 70px;height: 25px;"/>';

	},

	normalTpl: function(headimg, username, content) {

		_content = danmuModule.cutDanmu(content);

		return '<div style="display: inline-block;vertical-align: middle;position: relative;">' +
			'<img src="' + pageInfo.path + '/static/resource/img/gn_r4_c1.png">' +
			'<img src="' + headimg + '" style="position: absolute;top: 5px;left: 10px;border-radius: 100px;height: 50px;width:50px">' +
			'</div>' +
			'<div style="border-radius: 20px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;">' + content + '</span>' +
			'</div>';
	},

	planeTpl: function(headimg, username, content) {
		return '<div style="display: inline-block;vertical-align: middle;position: relative;">' +
			'<img src="http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/newyearhead.png"/>' +
			'<img src="' + headimg + '" onerror="errorImg(this)" style="position: absolute;top: 8px;left: 8px;border-radius: 100px;height: 50px;width:50px">' +
			'</div>' +
			'<div style="border-radius: 26px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;border:2px solid #ffe400;background-color:rgba(211,16,16,0.85)">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;">' + content + '</span>' +
			'</div>' +
			'<img src ="' + pageInfo.path + 'static/resource/img/gift/plane/gifttitle.png' + '" style="position:absolute;top: -27px;left: 82px;height: 35px;"/>';
	},

	danmuSwitch: function() {
		var that = this;
		if(that.setting.isOpen) {
			that.setting.isOpen = false;
//			layer.msg('弹幕已经关闭');
		} else {
			that.setting.isOpen = true;
//			layer.msg('弹幕已经开启');
		}
	},

	//发送 普通弹幕
	postDanmu: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.position = "absolute";
		var h = that.randomNum(70, 350);
		//		if(pageInfo.zoom<1||pageInfo.needResize){
		//			div.style.left =1280/1.5 +'px';
		//			div.style.zoom = 1.5;
		//			div.style.top = h + 'px';
		//		}else{
		//			div.style.zoom = pageInfo.zoom*1.5;
		//			div.style.left = window.innerWidth/(pageInfo.zoom*1.5)+ 'px';
		//			div.style.top = h + 'px';
		//		}

		div.style.zoom = 1.5 * 1.5;
		div.style.left = 1920 / (1.5 * 1.5) + 'px';
		div.style.top = h + 'px';

		//div.style.top = h + 'px';
		//		div.innerHTML = '<div style="display: inline-block;vertical-align: middle;position: relative;"><img src="'+pageInfo.path+'/static/resource/img/gn_r4_c1.png" style="" />' +
		//			'<img src="' + headimg + '" style="position: absolute;top: 5px;left: 10px;border-radius: 100px;height: 50px;width:50px" />' +
		//			'</div>' +
		//			'<div style="border-radius: 20px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;">' +
		//			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
		//			'<span style="display: block;word-break: keep-all;">' + that.cutDanmu(content) + '</span>' +
		//			'</div>';

		div.innerHTML = that.newyearTpl(headimg, username, content);

		//document.getElementsByTagName('body')[0].appendChild(div);
		document.getElementById('hdApp').appendChild(div);
		var w = pageInfo.zoom < 1 || pageInfo.needResize ? div.offsetWidth + 1280 : div.offsetWidth + window.innerWidth;
		w = 1920;
		div.style.transition = "transform " + that.setting.time + "ms linear";
		div.style.transform = 'translateX(-' + (w / 1.5) + 'px)';

		console.log(w);
		console.log(div.offsetWidth);
		console.log($(div).width());
		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	postPlaneDanmu: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.position = "absolute";
		var h = that.randomNum(70, 350);

		div.style.zoom = 1.5 * 1.5;
		div.style.left = 1920 / (1.5 * 1.5) + 'px';
		div.style.top = h + 'px';
		div.style.zIndex = 800;

		div.innerHTML = that.planeTpl(headimg, username, content);
		document.getElementById('hdApp').appendChild(div);
		//document.getElementsByTagName('body')[0].appendChild(div);
		//document.querySelector('.screen-gift-warp').appendChild(div);
		//var w = pageInfo.zoom < 1 || pageInfo.needResize ? div.offsetWidth + 1280 : div.offsetWidth + window.innerWidth;
		var w = 1920 / div.style.zoom + div.offsetWidth * div.style.zoom;
		div.style.transition = "transform " + that.setting.time + "ms linear";
		//div.style.transform = 'translateX(-' + (w / 1.5) + 'px)';
		div.style.transform = 'translateX(-' + w + 'px)';

		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	postNewYearDanmu: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.position = "absolute";
		var h = that.randomNum(70, 350);

		div.style.zoom = 1.5 * 1.5;
		div.style.left = 1920 / (1.5 * 1.5) + 'px';
		div.style.top = h + 'px';
		div.innerHTML = ''
	},

	//APP端 发红包  大屏显示  wuyong
	postUFO: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.zIndex = '900';
		div.style.position = "fixed";
		div.style.left = window.innerWidth + 'px';
		var h = that.randomNum(0, 400);
		//var h = that.getRandom(400, 0)
		div.style.top = h + 'px';
		div.style.width = '300px';
		div.innerHTML = '<div style="display: inline-block;vertical-align: middle;position: relative;" class="ufo"><img src="' + pageInfo.path + '/static/resource/img/hb-ufo.png" style="width: 100%;height: 100%;" />' +
			'<img src="' + headimg + '" style="position: absolute;top: 10px;left: 80px;border-radius: 100px;height: 50px;width:50px" />' +
			'</div>' +
			'<div style="width:200px;border-radius: 20px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;">' + content + '</span>' +
			'</div>';
		document.getElementsByTagName('body')[0].appendChild(div);

		var w = div.offsetWidth + window.innerWidth;
		console.log(w);
		div.style.transition = "transform " + that.setting.time + "ms linear";
		div.style.transform = 'translateX(-' + w + 'px)'

		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	//A小红包 wuyong
	postDanmuHongbao: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.position = "fixed";
		div.style.zoom = pageInfo.zoom;
		div.style.left = window.innerWidth / pageInfo.zoom + 'px';
		//div.style.top = that.getRandom(580, 70) + 'px';
		div.style.top = that.randomNum(70, 450) + 'px';
		div.style.width = '240px';
		div.innerHTML = '	<div style="display: inline-block;vertical-align: middle;position: relative;"><img src="' + pageInfo.path + '/static/resource/img/gn_r4_c1.png" style="" />' +
			'<img src="' + headimg + '" style="position: absolute;top: 5px;left: 10px;border-radius: 100px;height: 50px;width:50px" />' +
			'</div>' +
			'<div style="border-radius: 20px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;position: relative; width: 250px;">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;color: yellow;">' + content + '</span>' +
			'<div class="danmu-hongbao danmu-hongbao-fly"></div>' +
			'</div>';
		document.getElementsByTagName('body')[0].appendChild(div);
		//		div.addEventListener('webkitAnimationEnd', function() {
		//			$(this).remove();
		//		})
		//var w = div.offsetWidth + window.innerWidth;
		var w = pageInfo.zoom < 1 || pageInfo.needResize ? div.offsetWidth + 1280 : div.offsetWidth + window.innerWidth;
		div.style.transition = "transform " + that.setting.time + "ms linear";
		div.style.transform = 'translateX(-' + w + 'px)'
		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	//祝福语
	postDanmuWish: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item danmumove';
		div.style.position = "fixed";

		if(pageInfo.zoom < 1 || pageInfo.needResize) {
			div.style.left = '1280px';
			div.style.zoom = 1.5;
		} else {
			div.style.zoom = pageInfo.zoom * 1.5; //祝福语1.5倍
			div.style.left = window.innerWidth / (pageInfo.zoom * 1.5) + 'px';
		}

		//div.style.top = that.getRandom(580, 70) + 'px';
		div.style.top = that.randomNum(70, 450) / 1.5 + 'px';

		div.style.width = '700px';
		div.innerHTML = '	<div style="display: inline-block;vertical-align: middle;position: relative;"><img src="' + pageInfo.path + '/static/resource/img/gn_r4_c1.png" style="" />' +
			'<img src="' + headimg + '" style="position: absolute;top: 5px;left: 10px;border-radius: 100px;height: 50px;width:50px" />' +
			'</div>' +
			'<div class="jianbian" style="border-radius: 20px; padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;position: relative;">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;color: yellow;">' + content + '</span>' +
			'</div>';
		document.getElementsByTagName('body')[0].appendChild(div);
		//		div.addEventListener('webkitAnimationEnd', function() {
		//			$(this).remove();
		//		})
		//var w = div.offsetWidth + window.innerWidth;
		var w = pageInfo.zoom < 1 || pageInfo.needResize ? div.offsetWidth + 1280 : div.offsetWidth + window.innerWidth;

		div.style.transition = "transform " + that.setting.time + "ms linear";
		div.style.transform = 'translateX(-' + w + 'px)';
		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	//霸屏弹幕
	postBigD: function(headimg, username, content, second, zoom) {
		zoom = zoom * 1.3;

		var that = this;
		var div = document.createElement("div");
		div.style.position = "absolute";

		//		if(pageInfo.zoom<1||pageInfo.needResize){
		//			div.style.left =1280/zoom +'px';
		//			div.style.zoom =zoom;
		//			div.style.top=that.randomNum(70, 300)/zoom+'px';
		//		}else{
		//			div.style.zoom = pageInfo.zoom*zoom;
		//			div.style.left = window.innerWidth/(pageInfo.zoom*zoom) + 'px';
		//			div.style.top = that.randomNum(70, 300)/zoom + 'px';
		//		}

		div.style.zoom = 1.5 * zoom;
		div.style.left = 1920 / (1.5 * zoom) + 'px';
		div.style.top = that.randomNum(70, 300) / zoom + 'px';

		//div.style.top = that.getRandom(580, 70) + 'px';

		div.style.width = '750px';
		div.innerHTML = '<div style="position: relative;display: inline-block;vertical-align: middle;">' +
			'<img src="' + pageInfo.path + '/static/resource/img/HUOJIAN.png" width="300"/>' +
			'<img src="' + headimg + '" style="height: 50px;width: 50px;border: 2px solid #FFF700;border-radius: 50%;position: absolute;left: 60px;top: 53px;"/>' +
			'</div>' +
			'<div style="border: 3px solid #F8CA2B; border-radius: 40px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;position: relative;">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;color: yellow;">' + content + '</span>' +
			'</div>';

		//document.getElementsByTagName('body')[0].appendChild(div);
		document.getElementById('hdApp').appendChild(div);
		//var w = (div.offsetWidth + window.innerWidth)/zoom;

		var w = pageInfo.zoom < 1 || pageInfo.needResize ? (div.offsetWidth + 1280) / zoom : (div.offsetWidth + window.innerWidth) / zoom;
		w = 1920;
		//		div.style.transition = 'transform '+second+'ms linear';
		div.style.transition = 'transform 15000ms linear';
		div.style.transform = 'translateX(-' + w + 'px)';
		//		div.style.transform = 'translateX(-500px)';

		setTimeout(function() {
			$(div).remove();
			//			div.style.transform = 'translateX(-' + w + 'px)';
			//					setTimeout(function() {
			//			$(div).remove();
			//		}, 5000)
		}, 15000)
	},

	postXrHb: function(headimg, username, money, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'xrhb ani-hb-down';

		if(pageInfo.zoom < 1 || pageInfo.needResize) {
			div.style.left = (randomNum(20, window.innerWidth - 20)) + 'px';
		} else {
			div.style.zoom = pageInfo.zoom;
			div.style.left = (randomNum(20, window.innerWidth - 20)) / pageInfo.zoom + 'px';
		}

		div.innerHTML = '<div style="position: relative;width: 100%;height: 100%;">' +
			'<img src="' + pageInfo.path + '/static/resource/img/GUANGM.png" style="position: absolute;top: -90px;width: 180px;left: 10px;" class="ani-guang"/>' +
			'<img class="ani-smallhb" src="' + pageInfo.path + '/static/resource/img/XIAOHONGB.png" style="position: absolute;top: 50px;left: -70px;width: 350px;z-index: 100;transform: scale(0);"/>' +
			'<img src="' + headimg + '" style="height: 50px;width: 50px;position: absolute;top: -20px;left: 75px;border: 3px solid #F8CA2B;border-radius: 40px;"/>' +
			'<img src="' + pageInfo.path + '/static/resource/img/HONGB.png" width="200" style="z-index: 110;"/>' +
			'<div style="color: #fff;text-align: center;padding: 5px 10px;margin-top: -290px;">' + username + '</div>' +
			'<div style="color: #F8CA2B;text-align: center;margin-top: 50px;font-size: 23px;padding: 5px 10px;">' + money + '元</div>' +
			'<div style="text-align: center;color: #fff;padding: 5px;">' + content + '</div>' +
			'<div style="text-align: center;color: #F8CA2B;padding: 5px;">祝新人新婚愉快</div></div>';

		document.getElementsByTagName('body')[0].appendChild(div);

		setTimeout(function() {
			$(div).remove();
		}, 8000)
	},

	//getRandom(1140,70); 随机数
	getRandom: function(n, m) {
		return Math.floor(Math.random() * n + 1) + m
	},

	//工具 限制弹幕字数
	cutDanmu: function(content) {
		if(content.length > 40) {
			return content.substr(0, 40) + ".."
		} else {
			return content;
		}
	},

	randomNum: function(n, m) {
		var random = Math.floor(Math.random() * (m - n)) + n;
		//		console.log("rewardNames[" + random + "]");
		return random;
	},
	sendDanmuByNum: function(num, headimg, username, content) {
		var time = 0;
		for(var i = 0; i < num; i++) {
			(function() {
				setTimeout(function() {
					danmuModule.postPlaneDanmu(headimg, username, content)
				}, time);
			})(time);
			time += 2000;
		}
	}
};var w = 190;
var l = 0;
var len = $("#rankList li").length * 2;

var theDefault={};
theDefault.first =[];
theDefault.second =[];
theDefault.third =[];
var _prizeUser = [];
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head1.png",renickname:"t1"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head2.png",renickname:"t2"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head3.png",renickname:"t3"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head4.png",renickname:"t4"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head5.png",renickname:"t5"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head6.png",renickname:"t6"})

var _allPrizeUser = [];

$.get(pageInfo.postUrl+'survey/list_prize?liveid='+pageInfo.roomId,function(e){
	//console.log(e);
	if(e.success){
		if(e.data&&e.data.length>0){
			for(var i =0;i<e.data.length;i++){
				if(e.data[i].prize == "1"){
					theDefault.first.push({img:"http://ocar2d7vc.bkt.clouddn.com/"+ e.data[i].head_img,name:e.data[i].username})
				}else if(e.data[i].prize == "2"){
					theDefault.second.push({img:"http://ocar2d7vc.bkt.clouddn.com/"+e.data[i].head_img,name:e.data[i].username})
				}else if(e.data[i].prize == "3"){
					theDefault.third.push({img:"http://ocar2d7vc.bkt.clouddn.com/"+e.data[i].head_img,name:e.data[i].username})
				}
				//载入图片
				var img = new Image();
				img.src = "http://ocar2d7vc.bkt.clouddn.com/"+ e.data[i].head_img;
			}
		}
	}
})

$.get(pageInfo.postUrl+'survey/list_draw?live_id='+pageInfo.roomId,function(e){
	console.log(e);
	if(e.success){
		for(var i =0;i<e.data.length;i++){
			_allPrizeUser.push({
				'renickname':e.data[i],
				'reheadimgurl':pageInfo.path+'static/resource/img/headimg/head'+randomNum(1,7)+'.png'
			})
		}
		$('.canyu').eq(0).html('参加抽奖人数:'+e.data.length);
	}
})



//theDefault.first.push({img:"http://localhost:8088/haidong-game//static/resource/img/headimg/head2.png",name:"陈兴"})
//theDefault.first.push({img:"http://localhost:8088/haidong-game//static/resource/img/headimg/head6.png",name:"大海之"})

function flagt() {
	var reg = /stardraw enddraw/g;
	var className = $(".stardraw").attr("class");
	return reg.test(className);
}

$("#rankList").append($("#rankList").html()).css({
	"width": len * w,
	"left": -len * w / 2
})

$("#passPre").click(function() {
//	var flag = flagt();
//	if(flag) return false;
	if($('.stardraw').hasClass('disabled'))
		return;
	l = parseInt($("#rankList").css("left")) + w; //这里要转成整数，因为后面带了px单位
	showCurrent(l);

});

$("#passNext").click(function() {
//	var flag = flagt();
//	if(flag) return false;
	if($('.stardraw').hasClass('disabled'))
		return;
	
	l = parseInt($("#rankList").css("left")) - w; //这里要转成整数，因为后面带了px单位
	showCurrent(l);
});

function showCurrent(l) {	
	if($("#rankList").is(':animated')) {
		return;
	}
	$("#rankList").animate({
			"left": l
		},
		500,
		function() {
			if(l == 0) {
				$("#rankList").css("left", -len * w / 2);
			} else if(l == (1 - len) * w) {
				$("#rankList").css('left', (1 - len / 2) * w);
			}
		});

}
var dataUser =[];

//for(var i=0; i<10;i++){
//	dataUser.push({headimgurl:'images/main/abc_03.png',nickname:'胡小林'+i})
//}

function getDrwaUser(){
	$.get(pageInfo.postUrl+"reportController/report/"+pageInfo.roomId, function(data) {
		$('#current-people').html(data.data.length + '');
		dataUser.length = 0;
		dataUser = data.data;
		if(data.data.length==0){
			layer.msg('当前还没有抽奖人数哦')
		}
		$('.canyu').eq(0).html('参加抽奖人数:'+data.data.length);
		$('.canyu').eq(1).html('获奖人数:0');
		$('#userList').empty();
	}, 'json').error(function() {
		layer.msg('抽奖初始化失败 请重试');
		dataUser.length = 0;
	})
}


function initLottery(force){
	if(force){
		if(_allPrizeUser.length>0){
			dataUser=simpleCopy(_allPrizeUser)
		}else{
			getDrwaUser();
		}
		$('.canyu').eq(1).html('获奖人数:0');
		$('#userList').empty();
	}else if(dataUser.length == 0){
	if(_allPrizeUser.length>0){
		dataUser=simpleCopy(_allPrizeUser)
		}else{
			getDrwaUser();
		}
	}
}


function toDraw() {
	$.ajax({
		url: 'http://' + chatHost + '/draw?liveid=' + UserInfo.roomId(),
		type: "get",
		dataType: "jsonp",
		jsonp: "callback", //服务端用于接收callback调用的function名的参数   
		jsonpCallback: "success_jsonpCallback", //callback的function名称,服务端会把名称和data一起传递回来  
		success: function(data) {
			//				            	var json =  JSON.stringify(data);
			dataUser = data.data;
			//						dataUser=[{
			//							
			//							headimgurl:"images/main/abc_03.png",
			//							nickname:"我的名字很长很长真的很长很长不骗你"
			//							
			//							
			//						}]			
			$(".luckdraw").css("display", "block");
			$('.jiangpingsan').show();

			$(".canyu").eq(0).html("参加抽奖人数：" + data.num)
			return dataUser;
			//				            	var nikname =[];
			//				            	var headimgurl =json.headimgurl;
		},
		error: function() {
			alert('Error123');
		}
	});
}

var timr;
$(".stardraw").click(function() {
	var flag = flagt(); //true 表示结束
	
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})
	
	
//	if(dataUser.length == 0){
//		layer.msg("已经抽完啦");
//	}
	
	
	if(flag) {

//		if(voice_draw.paused) {
//			voice_draw.play();
//		}
		clearInterval(timr);
	//	$(this).removeClass("enddraw");
		$(this).removeClass("disabled");
		console.log("抽奖结束");
		var classNam = "firstluck"
		if(l == 0 || l == -570) {
			console.log("我是一等奖")
			classNam = "firstluck"
		} else if(l == -190 || l == -760) {
			classNam = "secondluck"
			console.log("我是二等奖")
		} else if(l == -950 || l == -380) {
			console.log("我是三等奖")
			classNam = "thridluck"
		}
		if(dataUser.length == 0) return false;
		var IdName = document.getElementById("changeImg");
		var scr = IdName.getAttribute("src");
		var title = IdName.getAttribute("title");
		if(title.length > 5) {
			title = title.substr(0, 4) + "..."
		}
		var spliceI = IdName.getAttribute("data-id");
		if(dataUser.length > 0) {
			dataUser.splice(spliceI, 1);
		}
		var li = document.createElement("li");
		var str = '<span class=\"' + classNam + '\"></span><span class="userImg">' +
			'<img src=\"' + scr + '\" /></span>' + title

		li.innerHTML = str;
		$("#userList").prepend(li);
		//						var h = parseFloat($(".postA").css("height"));
		//						var len = parseFloat($("#userList").css("height"));
		//						console.log("h:" + h + ";" + "len:" + len)
		//						if(len > h) {
		//							var topL = h - len;
		//							console.log(topL)
		//							$("#userList").css("top", topL);
		//						};
		var unme = $("#userList li").length;
		$(".canyu").eq(1).html("获奖人数：" + unme)

	} else {

		$(this).addClass("disabled");
		var len = dataUser.length;
		var IdName = document.getElementById("changeImg");
		var Lname =document.getElementById("lotteryname"); 
	
			timr = setInterval(retimer, 50);

			function retimer() {
				
				if(dataUser.length<1){
					var nun = Math.floor(Math.random() * _prizeUser.length);
					IdName.setAttribute("src", _prizeUser[nun].reheadimgurl);
					IdName.setAttribute("title", _prizeUser[nun].renickname);
					IdName.setAttribute("data-id", nun);
				}else{
					var nun = Math.floor(Math.random() * len);
					IdName.setAttribute("src", dataUser[nun].reheadimgurl);
					IdName.setAttribute("title", dataUser[nun].renickname);
					//IdName.setAttribute("title",'halo12');
					IdName.setAttribute("data-id", nun);
					Lname.innerHTML = dataUser[nun].renickname;
				}
			}
	
		
		var that = this;
		setTimeout(function(){
			//$(".stardraw").click();
				clearInterval(timr);
			//	$(this).removeClass("enddraw");
				$(that).removeClass("disabled");
				console.log("抽奖结束");
				var classNam = "firstluck";
				
				var place = 0;
				
				if(l == 0 || l == -570) {
					console.log("我是一等奖")
					classNam = "firstluck";
					place = 1;
				} else if(l == -190 || l == -760) {
					classNam = "secondluck"
					console.log("我是二等奖")
					place = 2;
				} else if(l == -950 || l == -380) {
					console.log("我是三等奖")
					classNam = "thridluck";
					place = 3;
				}
				if(dataUser.length == 0 && place==0) {
					layer.msg("已经抽完啦");
					return false;
				}
					
				//is default
				var defaultImg ="";
				var defaultName = "";
				
				//先抽取内定中奖人
				if(place == 1){
					if(theDefault.first.length>0){
						var temp = theDefault.first.pop();
						defaultImg = temp.img;
						defaultName = temp.name;
					}
				}else if(place == 2){
					if(theDefault.second.length>0){
						var temp = theDefault.second.pop();
						defaultImg = temp.img;
						defaultName = temp.name;
					}
					
				}else if(place == 3){
					if(theDefault.third.length>0){
						var temp = theDefault.third.pop();
						defaultImg = temp.img;
						defaultName = temp.name;
					}
				}
				
				
				if(defaultImg!=""&&defaultName!=""){
					
					//检查相似度
					for(var i =0 ;i<dataUser.length;i++){
						if(Levenshtein_Distance_Percent(defaultName,dataUser[i].renickname)>0.6){
							dataUser.splice(i, 1);
							break;
						}
					}
					
					var IdName = document.getElementById("changeImg");
					IdName.src=defaultImg;
					Lname.innerHTML = defaultName;
					
					
					if(defaultName.length > 5) {
						defaultName = defaultName.substr(0, 4) + "..."
					}
					
					
					

					var li = document.createElement("li");
					var str = '<span class=\"' + classNam + '\"></span><span class="userImg">' +
						'<img src=\"' + defaultImg + '\" /></span>' + defaultName

					li.innerHTML = str;
					$("#userList").prepend(li);
				
					var unme = $("#userList li").length;
					$(".canyu").eq(1).html("获奖人数：" + unme);
					//弹出中奖名单
					showLotteryResult(defaultImg,defaultName);
					
				}else{
					
					if(dataUser.length==0){
						layer.msg("已经抽完啦！");
						return ;
					}
					
					//检查 中奖人在内定中
//					theDefault.first =[];
//					theDefault.second =[];
//					theDefault.third =[];
					
					_checkPrizeUser(document.getElementById("changeImg").getAttribute("title"),classNam);
					
					
					
					
//					var IdName = document.getElementById("changeImg");
//					var scr = IdName.getAttribute("src");
//					var title = IdName.getAttribute("title");
//					if(title.length > 5) {
//						title = title.substr(0, 4) + "..."
//					}
//					var spliceI = IdName.getAttribute("data-id");
//					if(dataUser.length > 0) {
//						dataUser.splice(spliceI, 1);
//					}
//					
//					var li = document.createElement("li");
//					var str = '<span class=\"' + classNam + '\"></span><span class="userImg">' +
//						'<img src=\"' + scr + '\" /></span>' + title
//
//					li.innerHTML = str;
//					$("#userList").prepend(li);
//				
//					var unme = $("#userList li").length;
//					$(".canyu").eq(1).html("获奖人数：" + unme)
//					//弹出中奖名单
//					showLotteryResult(scr,title);
				}
				
				
		},5000)
		

	}

});
$("#reset").click(function() {
	var flag = flagt();
	//				console.log(flag)
	if(flag) {
		return false;
	}
	$("#userList").html("")
})
$("#closebtn").click(function() {
	$(this).parent().css("display", "none");
	$('.jiangpingsan').hide();
});

function gameManExist(userid) {
	for(var i = 0; i < gameManList.length; i++) {
		if(userid == gameManList[i]) {
			return true;
		}
	}
	gameManList.push(userid);
	return false;
}
//theDefault.first =[];
//theDefault.second =[];
//theDefault.third =[];

function _checkPrizeUser(name,classNam){
	console.log('_checkPrizeUser:'+name);
	var IdName = document.getElementById("changeImg");
	var Lname =document.getElementById("lotteryname"); 
	for(var i =0;i<theDefault.first.length;i++){
		if(Levenshtein_Distance_Percent(theDefault.first[i].name,name)>0.6){
			setTimeout(function(){
				var nun = Math.floor(Math.random() * dataUser.length);
				IdName.setAttribute("src", dataUser[nun].reheadimgurl);
				IdName.setAttribute("title", dataUser[nun].renickname);
				IdName.setAttribute("data-id", nun);
				Lname.innerHTML = dataUser[nun].renickname;
				_checkPrizeUser(dataUser[nun].renickname);
			},50)
			
			return;
		}
	}
	
	for(var j =0;j<theDefault.second.length;j++){
		if(Levenshtein_Distance_Percent(theDefault.first[j].name,name)>0.6){
			setTimeout(function(){
				var nun = Math.floor(Math.random() * dataUser.length);
				IdName.setAttribute("src", dataUser[nun].reheadimgurl);
				IdName.setAttribute("title", dataUser[nun].renickname);
				IdName.setAttribute("data-id", nun);
				Lname.innerHTML = dataUser[nun].renickname;
				_checkPrizeUser(dataUser[nun].renickname);
			},50)
		
			return;
		}
	}
	for(var k =0;k<theDefault.third.length;k++){
		if(Levenshtein_Distance_Percent(theDefault.first[k].name,name)>0.6){
			setTimeout(function(){
				var nun = Math.floor(Math.random() * dataUser.length);
				IdName.setAttribute("src", dataUser[nun].reheadimgurl);
				IdName.setAttribute("title", dataUser[nun].renickname);
				IdName.setAttribute("data-id", nun);
				Lname.innerHTML = dataUser[nun].renickname;
				_checkPrizeUser(dataUser[nun].renickname);
			},50)
		
			return;
		}
	}
	
	var scr = IdName.getAttribute("src");
	var title = IdName.getAttribute("title");
	console.log(title);
	if(title.length > 5) {
		title = title.substr(0, 4) + "..."
	}
	var spliceI = IdName.getAttribute("data-id");
	if(dataUser.length > 0) {
		dataUser.splice(spliceI, 1);
	}
	
	var li = document.createElement("li");
	var str = '<span class=\"' + classNam + '\"></span><span class="userImg">' +
		'<img src=\"' + scr + '\" /></span>' + title

	li.innerHTML = str;
	$("#userList").prepend(li);

	var unme = $("#userList li").length;
	$(".canyu").eq(1).html("获奖人数：" + unme)
	//弹出中奖名单
	showLotteryResult(scr,title);
	
	
}



function Levenshtein_Distance(s, t) {
	var n = s.length; // length of s
	var m = t.length; // length of t
	var d = []; // matrix
	var i; // iterates through s
	var j; // iterates through t
	var s_i; // ith character of s
	var t_j; // jth character of t
	var cost; // cost

	if(n == 0) return m;
	if(m == 0) return n;

	for(i = 0; i <= n; i++) {
		d[i] 	= [];
		d[i][0] = i;
	}
	for(j = 0; j <= m; j++) {
		d[0][j] = j;
	}

	for(i = 1; i <= n; i++) {
		s_i = s.charAt(i - 1);
		for(j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			if(s_i == t_j) {
				cost = 0;
			} else {
				cost = 1;
			}

			d[i][j] = Minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
		}
	}

	return d[n][m];
	
}

//求两个字符串的相似度,返回相似度百分比
function Levenshtein_Distance_Percent(s, t) {
	var l = s.length > t.length ? s.length : t.length;
	var d = Levenshtein_Distance(s, t);
	console.log((1 - d / l).toFixed(3));
	return(1 - d / l).toFixed(3);
}

//求三个数字中的最小值
function Minimum(a, b, c) {
	return a < b ? (a < c ? a : c) : (b < c ? b : c);
}


function showLotteryResult(img,name){
	//alert('show');
	console.log("show");
	$('.lottery-result .lhead').attr('src',img);
	$('.lottery-result .lname').html(name);
	$('.lottery-result').show();
	$('.lottery-result').addClass('fadeIn');
	setTimeout(hideLotteryResult,5000)
}

function hideLotteryResult(){
	console.log("hide");
	$('.lottery-result').addClass('fadeOut');
	setTimeout(function(){
		$('.lottery-result').hide();
		$('.lottery-result').removeClass('fadeOut');
		$('.lottery-result').removeClass('fadeIn');
	},1000);
	
}



/* snabbt.js Version: 0.6.4 Build date: 2015-12-27 (c) 2015 Daniel Lundin @license MIT */
!function(t){if("object"==typeof exports&&"undefined"!=typeof module)module.exports=t();else if("function"==typeof define&&define.amd)define([],t);else{var n;n="undefined"!=typeof window?window:"undefined"!=typeof global?global:"undefined"!=typeof self?self:this,n.snabbt=t()}}(function(){return function t(n,e,i){function r(a,s){if(!e[a]){if(!n[a]){var u="function"==typeof require&&require;if(!s&&u)return u(a,!0);if(o)return o(a,!0);var c=new Error("Cannot find module '"+a+"'");throw c.code="MODULE_NOT_FOUND",c}var f=e[a]={exports:{}};n[a][0].call(f.exports,function(t){var e=n[a][1][t];return r(e?e:t)},f,f.exports,t,n,e,i)}return e[a].exports}for(var o="function"==typeof require&&require,a=0;a<i.length;a++)r(i[a]);return r}({1:[function(){!function(){if(!Array.prototype.find){var t=function(t){var n=Object(this),e=n.length<0?0:n.length>>>0;if(0===e)return void 0;if("function"!=typeof t||"[object Function]"!==Object.prototype.toString.call(t))throw new TypeError("Array#find: predicate must be a function");for(var i,r=arguments[1],o=0;e>o;o++)if(i=n[o],t.call(r,i,o,n))return i;return void 0};if(Object.defineProperty)try{Object.defineProperty(Array.prototype,"find",{value:t,configurable:!0,enumerable:!1,writable:!0})}catch(n){}Array.prototype.find||(Array.prototype.find=t)}}(this)},{}],2:[function(t,n){"use strict";function e(t,n,e,i){var s=r.optionOrDefault(e.duration,500),u=r.optionOrDefault(e.delay,0),c=o.createEaser(r.optionOrDefault(e.easing,"linear"),e),f=0===s?n.clone():t.clone();f.transformOrigin=e.transformOrigin,f.perspective=e.perspective;var p=-1,l=0,h=!1,d=u/s,m=e.manual,v=0,g=void 0,A=void 0;return A=e.valueFeeder?a.createValueFeederTweener(e.valueFeeder,t,n,f):a.createStateTweener(t,n,f),{options:e,endState:function(){return n},finish:function(t){m=!1;var n=s*v;p=l-n,g=t,c.resetFrom(v)},rollback:function(t){m=!1,A.setReverse();var n=s*(1-v);p=l-n,g=t,c.resetFrom(v)},tick:function(t){if(m)return l=t,this.updateCurrentTransform();if(-1===p&&(p=t),t-p>=u){!h&&e.start&&e.start(),h=!0,l=t-u;var n=Math.min(Math.max(0,l-p),s);c.tick(0===s?1:n/s),this.updateCurrentTransform(),e.update&&e.update(n/s),this.completed()&&g&&g()}},getCurrentState:function(){return f},setValue:function(t){h=!0,v=Math.min(Math.max(t,1e-4),.9999+d)},updateCurrentTransform:function(){var t=c.getValue();if(m){var n=Math.max(1e-5,v-d);c.isSpring?t=n:(c.tick(n,!0),t=c.getValue())}A.tween(t)},completed:function(){return 0===p?!1:c.completed()},updateElement:function(t,n){if(h||n){var o=A.asMatrix(),a=A.getProperties();r.updateElementTransform(t,o,i,a.perspective,e.staticTransform),r.updateElementProperties(t,a)}}}}function i(t){var n=t.movement;t.initialVelocity=.1,t.equilibriumPosition=0;var e=o.createSpringEasing(t),i=n.position,a=n.rotation,u=n.rotationPost,c=n.scale,f=n.skew,p=s.createState({position:i?[0,0,0]:void 0,rotation:a?[0,0,0]:void 0,rotationPost:u?[0,0,0]:void 0,scale:c?[1,1]:void 0,skew:f?[0,0]:void 0});return{options:function(){return t},tick:function(){e.equilibrium||(e.tick(),this.updateMovement())},updateMovement:function(){var t=e.getValue();i&&(p.position[0]=n.position[0]*t,p.position[1]=n.position[1]*t,p.position[2]=n.position[2]*t),a&&(p.rotation[0]=n.rotation[0]*t,p.rotation[1]=n.rotation[1]*t,p.rotation[2]=n.rotation[2]*t),u&&(p.rotationPost[0]=n.rotationPost[0]*t,p.rotationPost[1]=n.rotationPost[1]*t,p.rotationPost[2]=n.rotationPost[2]*t),c&&(p.scale[0]=1+n.scale[0]*t,p.scale[1]=1+n.scale[1]*t),f&&(p.skew[0]=n.skew[0]*t,p.skew[1]=n.skew[1]*t)},updateElement:function(t){r.updateElementTransform(t,p.asMatrix()),r.updateElementProperties(t,p.getProperties())},getCurrentState:function(){return p},completed:function(){return e.completed()}}}var r=t("./utils.js"),o=t("./easing.js"),a=t("./tweeners"),s=t("./state.js");n.exports={createAnimation:e,createAttentionAnimation:i}},{"./easing.js":3,"./state.js":8,"./tweeners":9,"./utils.js":10}],3:[function(t,n){"use strict";function e(t){return t}function i(t){return(Math.cos(t*Math.PI+Math.PI)+1)/2}function r(t){return t*t}function o(t){return-Math.pow(t-1,2)+1}function a(t,n){if("spring"===t)return u(n);var e=t;s.isFunction(t)||(e=c[t]);var i,r=e,o=0;return{tick:function(t){o=r(t),i=t},resetFrom:function(){i=0},getValue:function(){return o},completed:function(){return i>=1?i:!1}}}var s=t("./utils.js"),u=function(t){var n=s.optionOrDefault(t.startPosition,0),e=s.optionOrDefault(t.equilibriumPosition,1),i=s.optionOrDefault(t.initialVelocity,0),r=s.optionOrDefault(t.springConstant,.8),o=s.optionOrDefault(t.springDeceleration,.9),a=s.optionOrDefault(t.springMass,10),u=!1;return{isSpring:!0,tick:function(t,s){if(0!==t&&!s&&!u){var c=-(n-e)*r,f=c/a;i+=f,n+=i,i*=o,Math.abs(n-e)<.001&&Math.abs(i)<.001&&(u=!0)}},resetFrom:function(t){n=t,i=0},getValue:function(){return u?e:n},completed:function(){return u}}},c={linear:e,ease:i,easeIn:r,easeOut:o};n.exports={createEaser:a,createSpringEasing:u}},{"./utils.js":10}],4:[function(t,n){"use strict";var e=t("./state.js").stateFromOptions,i=t("./animation.js"),r=t("./state.js").createState,o=t("./utils.js"),a={runningAnimations:[],completedAnimations:[],transformProperty:"transform",rAFScheduled:!1,init:function(){if(void 0===typeof window){var t=window.getComputedStyle(document.documentElement,""),n=(Array.prototype.slice.call(t).join("").match(/-(moz|webkit|ms)-/)||""===t.OLink&&["","o"])[1];"webkit"===n&&(this.transformProperty="webkitTransform")}},scheduleNextFrame:function(){var t=this;this.rAFScheduled||(this.rAFScheduled=!0,window.requestAnimationFrame(function(n){t.rAFScheduled=!1,t.stepAnimations(n)}))},stepAnimations:function(t){var n=this;this.runningAnimations.forEach(function(e){var i=e[0],r=e[1];n.stepAnimation(i,r,t)}),this.archiveCompletedAnimations(),this.runningAnimations.length>0&&this.scheduleNextFrame()},stepAnimation:function(t,n,e){n.tick(e),n.updateElement(t)},archiveCompletedAnimations:function(){var t=this.runningAnimations.filter(function(t){return!t[1].completed()}),n=this.runningAnimations.filter(function(t){return t[1].completed()}),e=this.createQueuedAnimations(n),i=n.filter(function(t){return!e.find(function(n){return n[0]!==t[0]})});a.runningAnimations=t,this.completedAnimations=this.completedAnimations.filter(function(t){return!i.find(function(n){return n[0]===t[0]})}),Array.prototype.push.apply(this.completedAnimations,i),Array.prototype.push.apply(this.runningAnimations,e),n.forEach(function(t){var n=t[1].options.complete;n&&n()}),this.clearOphanedEndStates()},createQueuedAnimations:function(t){var n=this,e=t.filter(function(t){var n=t[2];return n.index<n.queue.length}).map(function(t){var e=t[0],i=t[2],r=i.queue[i.index];return i.index++,[t[0],n.createAnimation(e,r,t[1].endState()),i]});return e},createChainer:function(){var t={index:0,queue:[],snabbt:function(n){return this.queue.push(n),t}};return t},createAnimation:function(t,n,r){var o=r||this.findCurrentState(t),a=e(n,o,!0),s=e(n,o,!1);this.runningAnimations=this.runningAnimations.filter(function(n){return t!==n[0]});var u=i.createAnimation(a,s,n,this.transformProperty);return u},createAttentionAnimation:function(t,n){var o=e(n,r({},!1));n.movement=o;var a=i.createAttentionAnimation(n);return a},stopAnimation:function(t){var n=this.runningAnimations.filter(function(n){return n[0]===t});this.runningAnimations=this.runningAnimations.filter(function(n){return n[0]!==t}),Array.prototype.push.apply(this.completedAnimations,n)},initializeAnimation:function(t,n,e){var i=void 0;if("attention"===n)i=this.createAttentionAnimation(t,e);else{if("stop"===n)return this.stopAnimation(t);i=this.createAnimation(t,n)}var r=this.createChainer();return i.updateElement(t,!0),this.runningAnimations.push([t,i,r]),this.scheduleNextFrame(),n.manual?i:r},findCurrentState:function(t){var n=this.runningAnimations.find(function(n){return t===n[0]});return n?n[1].getCurrentState():(n=this.completedAnimations.find(function(n){return t===n[0]}),n?n[1].getCurrentState():void 0)},clearOphanedEndStates:function(){this.completedAnimations=this.completedAnimations.filter(function(t){return o.findUltimateAncestor(t[0]).body})}};n.exports=a},{"./animation.js":2,"./state.js":8,"./utils.js":10}],5:[function(t,n){"use strict";function e(t,n,e){if(!t.length)return"string"==typeof n?i.initializeAnimation(t,n,r(e,0,1)):i.initializeAnimation(t,r(n,0,1),e);for(var a=[],s={snabbt:function(t){var n=a.length;return a.forEach(function(e,i){e.snabbt(r(t,i,n))}),s},setValue:function(t){return a.forEach(function(n){n.setValue(t)}),s},finish:function(t){return a.forEach(function(n,e){return o.isFunction(t)?n.finish(function(){t(e,a.length)}):void n.finish()}),s},rollback:function(t){return a.forEach(function(n,e){return o.isFunction(t)?n.rollback(function(){t(e,a.length)}):void n.rollback()}),s}},u=0,c=t.length;c>u;++u)a.push("string"==typeof n?i.initializeAnimation(t[u],n,r(e,u,c)):i.initializeAnimation(t[u],r(n,u,c),e));return s}t("array.prototype.find");var i=t("./engine.js"),r=t("./properties.js").preprocessOptions,o=t("./utils.js"),a=t("./matrix.js"),s=t("./utils.js").updateElementTransform;n.exports=function(t,n,i){return e(t,n,i)},n.exports.createMatrix=a,n.exports.setElementTransform=s,n.exports.sequence=function(t){var n=-1,i=function r(){if(++n,!(n>t.length-1)){var i=t[n][0],o=t[n][1],a=o.allDone;o.allDone=a?function(){a(),r()}:r,e(i,o)}};i()},"undefined"!=typeof window&&window.jQuery&&!function(t){t.fn.snabbt=function(t,n){return e(this.get(),t,n)}}(window.jQuery),i.init()},{"./engine.js":4,"./matrix.js":6,"./properties.js":7,"./utils.js":10,"array.prototype.find":1}],6:[function(t,n){"use strict";function e(t,n,e){return e[0]=t[0]*n[0]+t[1]*n[4]+t[2]*n[8]+t[3]*n[12],e[1]=t[0]*n[1]+t[1]*n[5]+t[2]*n[9]+t[3]*n[13],e[2]=t[0]*n[2]+t[1]*n[6]+t[2]*n[10]+t[3]*n[14],e[3]=t[0]*n[3]+t[1]*n[7]+t[2]*n[11]+t[3]*n[15],e[4]=t[4]*n[0]+t[5]*n[4]+t[6]*n[8]+t[7]*n[12],e[5]=t[4]*n[1]+t[5]*n[5]+t[6]*n[9]+t[7]*n[13],e[6]=t[4]*n[2]+t[5]*n[6]+t[6]*n[10]+t[7]*n[14],e[7]=t[4]*n[3]+t[5]*n[7]+t[6]*n[11]+t[7]*n[15],e[8]=t[8]*n[0]+t[9]*n[4]+t[10]*n[8]+t[11]*n[12],e[9]=t[8]*n[1]+t[9]*n[5]+t[10]*n[9]+t[11]*n[13],e[10]=t[8]*n[2]+t[9]*n[6]+t[10]*n[10]+t[11]*n[14],e[11]=t[8]*n[3]+t[9]*n[7]+t[10]*n[11]+t[11]*n[15],e[12]=t[12]*n[0]+t[13]*n[4]+t[14]*n[8]+t[15]*n[12],e[13]=t[12]*n[1]+t[13]*n[5]+t[14]*n[9]+t[15]*n[13],e[14]=t[12]*n[2]+t[13]*n[6]+t[14]*n[10]+t[15]*n[14],e[15]=t[12]*n[3]+t[13]*n[7]+t[14]*n[11]+t[15]*n[15],e}function i(t,n,e,i){t[0]=1,t[1]=0,t[2]=0,t[3]=0,t[4]=0,t[5]=1,t[6]=0,t[7]=0,t[8]=0,t[9]=0,t[10]=1,t[11]=0,t[12]=n,t[13]=e,t[14]=i,t[15]=1}function r(t,n){t[0]=1,t[1]=0,t[2]=0,t[3]=0,t[4]=0,t[5]=Math.cos(n),t[6]=-Math.sin(n),t[7]=0,t[8]=0,t[9]=Math.sin(n),t[10]=Math.cos(n),t[11]=0,t[12]=0,t[13]=0,t[14]=0,t[15]=1}function o(t,n){t[0]=Math.cos(n),t[1]=-Math.sin(n),t[2]=0,t[3]=0,t[4]=Math.sin(n),t[5]=Math.cos(n),t[6]=0,t[7]=0,t[8]=0,t[9]=0,t[10]=1,t[11]=0,t[12]=0,t[13]=0,t[14]=0,t[15]=1}function a(t,n,e){t[0]=1,t[1]=Math.tan(n),t[2]=0,t[3]=0,t[4]=Math.tan(e),t[5]=1,t[6]=0,t[7]=0,t[8]=0,t[9]=0,t[10]=1,t[11]=0,t[12]=0,t[13]=0,t[14]=0,t[15]=1}function s(t,n,e){t[0]=n,t[1]=0,t[2]=0,t[3]=0,t[4]=0,t[5]=e,t[6]=0,t[7]=0,t[8]=0,t[9]=0,t[10]=1,t[11]=0,t[12]=0,t[13]=0,t[14]=0,t[15]=1}function u(t){t[0]=1,t[1]=0,t[2]=0,t[3]=0,t[4]=0,t[5]=1,t[6]=0,t[7]=0,t[8]=0,t[9]=0,t[10]=1,t[11]=0,t[12]=0,t[13]=0,t[14]=0,t[15]=1}function c(t,n){n[0]=t[0],n[1]=t[1],n[2]=t[2],n[3]=t[3],n[4]=t[4],n[5]=t[5],n[6]=t[6],n[7]=t[7],n[8]=t[8],n[9]=t[9],n[10]=t[10],n[11]=t[11],n[12]=t[12],n[13]=t[13],n[14]=t[14],n[15]=t[15]}function f(){var t=new Float32Array(16),n=new Float32Array(16),f=new Float32Array(16);return u(t),{data:t,asCSS:function(){for(var n="matrix3d(",e=0;15>e;++e)n+=Math.abs(t[e])<1e-4?"0,":t[e].toFixed(10)+",";return n+=Math.abs(t[15])<1e-4?"0)":t[15].toFixed(10)+")"},clear:function(){u(t)},translate:function(r,o,a){return c(t,n),i(f,r,o,a),e(n,f,t),this},rotateX:function(i){return c(t,n),r(f,i),e(n,f,t),this},rotateY:function(i){return c(t,n),p(f,i),e(n,f,t),this},rotateZ:function(i){return c(t,n),o(f,i),e(n,f,t),this},scale:function(i,r){return c(t,n),s(f,i,r),e(n,f,t),this},skew:function(i,r){return c(t,n),a(f,i,r),e(n,f,t),this}}}var p=function(t,n){t[0]=Math.cos(n),t[1]=0,t[2]=Math.sin(n),t[3]=0,t[4]=0,t[5]=1,t[6]=0,t[7]=0,t[8]=-Math.sin(n),t[9]=0,t[10]=Math.cos(n),t[11]=0,t[12]=0,t[13]=0,t[14]=0,t[15]=1};n.exports=f},{}],7:[function(t,n){"use strict";function e(t){return"from"+t.charAt(0).toUpperCase()+t.slice(1)}function i(t,n,i){if(!t)return t;var o=r.cloneObject(t),a=r.isFunction(t.allDone),s=r.isFunction(t.complete);(s||a)&&(o.complete=function(){s&&t.complete.call(this,n,i),a&&n===i-1&&t.allDone()}),r.isFunction(t.valueFeeder)&&(o.valueFeeder=function(e,r){return t.valueFeeder(e,r,n,i)}),r.isFunction(t.easing)&&(o.easing=function(e){return t.easing(e,n,i)}),r.isFunction(t.start)&&(o.start=function(){return t.start(n,i)}),r.isFunction(t.update)&&(o.update=function(e){return t.update(e,n,i)});var c=Object.keys(u).concat(["perspective","transformOrigin","duration","delay"]);return c.forEach(function(a){var s=e(a);r.isFunction(t[a])&&(o[a]=t[a](n,i)),r.isFunction(t[s])&&(o[s]=t[s](n,i))}),o}var r=t("./utils.js"),o=1,a=2,s=3,u={position:[s,[0,0,0]],rotation:[s,[0,0,0]],rotationPost:[s,[0,0,0]],skew:[a,[0,0]],scale:[a,[1,1]],scalePost:[a,[1,1]],opacity:[o],width:[o],height:[o]};n.exports={tweenableProperties:u,fromPrefixed:e,preprocessOptions:i,types:{SCALAR:o,ARRAY_2:a,ARRAY_3:s}}},{"./utils.js":10}],8:[function(t,n){"use strict";function e(t,n){var i=r(),a={opacity:void 0,width:void 0,height:void 0,perspective:void 0},c={clone:function(){var t=this,n={};return Object.keys(o).forEach(function(e){var i=o[e][0];t[e]&&(n[e]=i===s.SCALAR?t[e]:t[e].slice(0))}),e(n)},asMatrix:function(){var t=i;return t.clear(),this.transformOrigin&&t.translate(-this.transformOrigin[0],-this.transformOrigin[1],-this.transformOrigin[2]),this.scale&&t.scale(this.scale[0],this.scale[1]),this.skew&&t.skew(this.skew[0],this.skew[1]),this.rotation&&(t.rotateX(this.rotation[0]),t.rotateY(this.rotation[1]),t.rotateZ(this.rotation[2])),this.position&&t.translate(this.position[0],this.position[1],this.position[2]),this.rotationPost&&(t.rotateX(this.rotationPost[0]),t.rotateY(this.rotationPost[1]),t.rotateZ(this.rotationPost[2])),this.scalePost&&t.scale(this.scalePost[0],this.scalePost[1]),this.transformOrigin&&t.translate(this.transformOrigin[0],this.transformOrigin[1],this.transformOrigin[2]),t},getProperties:function(){return a.opacity=this.opacity,a.width=this.width+"px",a.height=this.height+"px",a.perspective=this.perspective,a}};return Object.keys(o).forEach(function(e){c[e]=n?u.optionOrDefault(t[e],o[e][1]):t[e]}),c}function i(t,n,i){var r=n&&n.clone()||e({},!0),s=i?a:function(t){return t};return Object.keys(o).forEach(function(n){r[n]=u.optionOrDefault(t[s(n)],r[n]),r[n]&&r[n].slice&&(r[n]=r[n].slice())}),r}var r=t("./matrix.js"),o=t("./properties.js").tweenableProperties,a=t("./properties.js").fromPrefixed,s=t("./properties.js").types,u=t("./utils.js");n.exports={createState:e,stateFromOptions:i}},{"./matrix.js":6,"./properties.js":7,"./utils.js":10}],9:[function(t,n){"use strict";function e(t,n,e){function i(t,n,e,i,r){var o=n[i][0]-t[i][0],a=n[i][1]-t[i][1],s=n[i][2]-t[i][2];e[i][0]=t[i][0]+r*o,e[i][1]=t[i][1]+r*a,e[i][2]=t[i][2]+r*s}function r(t,n,e,i,r){var o=n[i][0]-t[i][0],a=n[i][1]-t[i][1];e[i][0]=t[i][0]+r*o,e[i][1]=t[i][1]+r*a}function s(t,n,e,i,r){var o=n[i]-t[i];e[i]=t[i]+r*o}var u=t,c=n,f=e,p=[];return Object.keys(o).forEach(function(t){void 0!==n[t]&&p.push(t)}),{tween:function(t){p.forEach(function(n){var e=o[n][0];e===a.ARRAY_3?i(u,c,f,n,t):e===a.ARRAY_2?r(u,c,f,n,t):s(u,c,f,n,t)})},asMatrix:function(){return f.asMatrix()},getProperties:function(){return f.getProperties()},result:function(){return f},setReverse:function(){var t=u;u=c,c=t}}}function i(t,n,e,i){var o=t(0,r()),a=n,s=e,u=i,c=!1;return{tween:function(n){c&&(n=1-n),o.clear(),o=t(n,o);var e=s.width-a.width,i=s.height-a.height,r=s.opacity-a.opacity;void 0!==s.width&&(u.width=a.width+n*e),void 0!==s.height&&(u.height=a.height+n*i),void 0!==s.opacity&&(u.opacity=a.opacity+n*r)},asMatrix:function(){return o},getProperties:function(){return u.getProperties()},setReverse:function(){c=!0}}}var r=t("./matrix.js"),o=t("./properties").tweenableProperties,a=t("./properties").types;n.exports={createStateTweener:e,createValueFeederTweener:i}},{"./matrix.js":6,"./properties":7}],10:[function(t,n){"use strict";function e(t){return"function"==typeof t}function i(t,n){return void 0===t?n:t}function r(t,n,e,i,r){var o=i?"perspective("+i+"px) ":"",a=n.asCSS(),s=r?r:"";e?t.style[e]=s+o+a:t.style.transform=s+o+a}function o(t){if(!t)return t;var n={};for(var e in t)n[e]=t[e];return n}function a(t){for(var n=t;n.parentNode;)n=n.parentNode;return n}var s=function(t,n){for(var e in n)"perspective"!==e&&(t.style[e]=n[e])};n.exports={optionOrDefault:i,updateElementTransform:r,updateElementProperties:s,isFunction:e,cloneObject:o,findUltimateAncestor:a}},{}]},{},[5])(5)});var signQ = {};
signQ.status = false;
signQ.queue = [];

var random3dArr = [];

//(function(arr) {
//	for(i = 0; i < 98; i++) {
//		arr.push(i)
//	}
//	arr.sort(function() { return 0.5 - Math.random() });
//})(random3dArr);

observe(signQ, function(name, value, old) {
	//	console.log(name + "__" + value + "__" + old);
	//	console.log(JSON.stringify(value))
	if(name == 'Array-unshift') {
		if(!signQ.status && signQ.queue.length > 0) {
			sign_usercome_3d()
		}
	}
})

function putuser_3d(data) {
	for(var i = 0; i < 98; i++) {
		random3dArr.push(i)
	}
	random3dArr.sort(function() { return 0.5 - Math.random() });

	for(var j = 0; j < data.length; j++) {
		if(random3dArr.length > 0) {
			var index = random3dArr.pop();
			$('.card').eq(index).find('img').attr('src',pageInfo.picHost+ data[j].reheadimgurl);
			if($('.card').hasClass('imgOut')) {
				$('.card').removeClass('imgOut').addClass('imgIn');
			}
		}
	}
}

function sign_usercome_3d() {
	if(signQ.queue.length <= 0) {
		signQ.status = false;
		return;
	} else {
		signQ.status = true;
	}
	console.log("start ani")
	var user = signQ.queue.pop();
	if(random3dArr.length <= 0) {
		for(var i = 0; i < 98; i++) {
			random3dArr.push(i)
		}
		random3dArr.sort(function() { return 0.5 - Math.random() });
	}
	var index = random3dArr.pop();
//	alert(index)
	console.log(index)
	$('.card').eq(index).find('img').attr('src',pageInfo.picHost+user.headimg);
	$('.card').removeClass('imgOut').addClass('imgIn');
	$('#bigCard').find('img').attr('src', user.headimg)
	$('#bigCard').find('span').html(user.username + '</br>' + user.wish);
	$('#bigCard').addClass('signani');
}

//document.getElementById("bigCard").addEventListener('webkitAnimationEnd', function() {
//	this.classList.remove('signani');
//	setTimeout(sign_usercome_3d, 1000);
//})

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



var hbNotice = {};
hbNotice.status = false;
hbNotice.queue = [];


observe(hbNotice, function(name, value, old) {
	if(name == 'Array-unshift') {
		if(!hbNotice.status && hbNotice.queue.length > 0) {
			startRed()
		}
	}
})

function startRed(){
	if(hbNotice.queue.length <= 0) {
		hbNotice.status = false;
		return;
	} else {
		hbNotice.status = true;
	}
	var c = hbNotice.queue.pop();
	
	
	$('.red-name').html(c.username);
	$('.red-content').html('抽中'+c.money+'元现金红包')
	
	$('.red-notice').addClass('zoomInDown');
	$('.red-notice').css('opacity','1');
	
	
	setTimeout(function(){
		$('.red-notice').removeClass('zoomInDown');
		$('.red-notice').css('opacity','0');
		setTimeout(startRed,500)
	},9000)
}
var turnWheel = {
	rewardNames: [], //转盘奖品名称数组
	colors: [], //转盘奖品区块对应背景颜色
	outsideRadius: 192, //转盘外圆的半径
	textRadius: 155, //转盘奖品位置距离圆心的距离
	insideRadius: 68, //转盘内圆的半径
	startAngle: 0, //开始角度
	bRotate: false //false:停止;ture:旋转
};


turnWheel.rewardNames = [
	"一等奖", "罚杯酒",
	"二等奖", "唱首歌",
	"三等奖", "唱首歌",
	"罚杯酒", "罚2杯酒",
	//		"20Q币 ", "30M流量包",
	//		"100M流量包", "2Q币"
];
turnWheel.colors = [
	"#FFF4D7", "#FFFFFF",
	"#F0F4D8", "#FFFFFF",
	"#FFF4D0", "#FFFFFF",
	"#FFF4D0", "#FFFFFF",
	//		"#FFF4D6", "#FFFFFF",
	//		"#FFF4D6", "#FFFFFF"
];

turnWheel.backgroundTimer = null;

//旋转转盘 item:奖品序号，从0开始的; txt：提示语 ,count 奖品的总数量;
function rotateFunc(item, tip, count) {

	// 应该旋转的角度，旋转插件角度参数是角度制。
	var baseAngle = 360 / count;
	// 旋转角度 == 270°（当前第一个角度和指针位置的偏移量） - 奖品的位置 * 每块所占的角度 - 每块所占的角度的一半(指针指向区域的中间)
	angles = 360 * 3 / 4 - (item * baseAngle) - baseAngle / 2; // 因为第一个奖品是从0°开始的，即水平向右方向
	$('#wheelCanvas').stopRotate();
	// 注意，jqueryrotate 插件传递的角度不是弧度制。
	// 哪个标签调用方法，旋转哪个控件
	$('#wheelCanvas').rotate({
		angle: 0, //初始旋转的角度数，并且立即执行
		animateTo: angles + 360 * 5, // 这里多旋转了5圈，圈数越多，转的越快
		duration: 8000, //指定使用animateTo的动画执行持续时间
		callback: function() { // 回调方法
			layer.msg(tip);
			clearInterval(turnWheel.backgroundTimer);
			$('.dzp-btn img').attr('src',pageInfo.path+'/static/resource/img/dzp/start.png');
			$('.dzp-btn img').removeClass('zuobiao-ani');
			turnWheel.bRotate = !turnWheel.bRotate;
//			$("#tip").text(tip);
//			turnWheel.bRotate = !turnWheel.bRotate;
//			if(isMobile.any()) // 判断是否移动设备
//			{
//				// 调OC代码
//				window.location.href = "turntable://test.com?" + "index=" + item + "&tip=" + tip;
//			}
		}
	});
};

// 抽取按钮按钮点击触发事件
$('.pointer').click(function() {
	// 正在转动，直接返回
	if(turnWheel.bRotate) return;
	
	
	turnWheel.backgroundTimer = setInterval(function(){
		if($('.content .wheel').hasClass('done')){
			$('.content .wheel').removeClass('done');
			$('.content .wheel').attr('style','background-image: url('+pageInfo.path+'/static/resource/img/dzp/PAN-02.png)');
		}else{
			$('.content .wheel').addClass('done');
			$('.content .wheel').attr('style','background-image: url('+pageInfo.path+'/static/resource/img/dzp/PAN-01.png)');
		}
	},500)
	
	
	$('.dzp-btn img').attr('src',pageInfo.path+'/static/resource/img/dzp/starting.png');
	$('.dzp-btn img').addClass('zuobiao-ani')
	
	turnWheel.bRotate = !turnWheel.bRotate;
	var count = turnWheel.rewardNames.length;

	// 这里应该是从服务器获取用户真实的获奖信息（对应的获奖序号）
	// 简单模拟随机获取奖品的序号(奖品个数范围内)
	var item = randomNum(0, count - 1);
	// 开始抽奖
	rotateFunc(item, turnWheel.rewardNames[item], count);
});

/*
返回在n和m之间的随机整数
n<= random <=m
*/
function randomNum(n, m) {
	/* Math.floor(Math.random()*10);时，可均衡获取0到9的随机整数。 */
	var random = Math.floor(Math.random() * (m - n)) + n;
//	console.log("rewardNames[" + random + "]");
	return random;
}

/*
 * 渲染转盘
 * */
function drawWheelCanvas() {

	// 获取canvas画板，相当于layer？？
	var canvas = document.getElementById("wheelCanvas");
	//    var canvas = ($("#wheelCanvas")).get()[0]; // 注意，jQuery获取的是包装过的对象，不是DOM对象,可以进行转换

	// 计算每块占的角度，弧度制
	var baseAngle = Math.PI * 2 / (turnWheel.rewardNames.length);
	// 获取上下文
	var ctx = canvas.getContext("2d");

	var canvasW = canvas.width; // 画板的高度
	var canvasH = canvas.height; // 画板的宽度
	//在给定矩形内清空一个矩形
	ctx.clearRect(0, 0, canvasW, canvasH);

	//strokeStyle 绘制颜色
	ctx.strokeStyle = "#FFBE04"; // 红色
	//font 画布上文本内容的当前字体属性
	ctx.font = '16px Microsoft YaHei';

	// 注意，开始画的位置是从0°角的位置开始画的。也就是水平向右的方向。
	// 画具体内容
	for(var index = 0; index < turnWheel.rewardNames.length; index++) {
		// 当前的角度
		var angle = turnWheel.startAngle + index * baseAngle;
		// 填充颜色
		ctx.fillStyle = turnWheel.colors[index];

		// 开始画内容
		// ---------基本的背景颜色----------
		ctx.beginPath();

		//ctx.fillStyle = '#EB852A'; 
		//		ctx.shadowOffsetX = 30; // 阴影Y轴偏移
		//		ctx.shadowOffsetY = 30; // 阴影X轴偏移
		// 		ctx.shadowBlur = 14; // 模糊尺寸
		// 		ctx.shadowColor = 'rgba(0, 0, 0, 0.5)'; // 颜色

		/*
		 * 画圆弧，和IOS的Quartz2D类似
		 * context.arc(x,y,r,sAngle,eAngle,counterclockwise);
		 * x :圆的中心点x
		 * y :圆的中心点x
		 * sAngle,eAngle :起始角度、结束角度
		 * counterclockwise : 绘制方向,可选，False = 顺时针，true = 逆时针
		 * */
		ctx.arc(canvasW * 0.5, canvasH * 0.5, turnWheel.outsideRadius, angle, angle + baseAngle, false);
		ctx.arc(canvasW * 0.5, canvasH * 0.5, turnWheel.insideRadius, angle + baseAngle, angle, true);

		ctx.fillStyle = '#FDB012'
		ctx.shadowBlur = 5;
		ctx.shadowColor = "#D3920E";
		//		ctx.fillStyle="blue";
		//		ctx.fillRect(20,20,100,80);

		ctx.stroke();

		ctx.fill();
		//保存画布的状态，和图形上下文栈类似，后面可以Restore还原状态（坐标还原为当前的0，0），
		ctx.save();
		//		ctx.restore();
		ctx.shadowBlur = 0;
		ctx.shadowColor = "#ffffff";

		/*----绘制奖品内容----重点----*/
		// 红色字体
//		ctx.fillStyle = "#E5302F";
		ctx.fillStyle = "#7E1B04";
		var rewardName = turnWheel.rewardNames[index];
		var line_height = 17;
		// translate方法重新映射画布上的 (0,0) 位置
		// context.translate(x,y);
		// 见PPT图片，
		var translateX = canvasW * 0.5 + Math.cos(angle + baseAngle / 2) * turnWheel.textRadius;
		var translateY = canvasH * 0.5 + Math.sin(angle + baseAngle / 2) * turnWheel.textRadius;
		ctx.translate(translateX, translateY);

		// rotate方法旋转当前的绘图，因为文字适合当前扇形中心线垂直的！
		// angle，当前扇形自身旋转的角度 +  baseAngle / 2 中心线多旋转的角度  + 垂直的角度90°
		ctx.rotate(angle + baseAngle / 2 + Math.PI / 2);

		/** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
		// canvas 的 measureText() 方法返回包含一个对象，该对象包含以像素计的指定字体宽度。
		// fillText() 方法在画布上绘制填色的文本。文本的默认颜色是黑色. fillStyle 属性以另一种颜色/渐变来渲染文本
		/*
		 * context.fillText(text,x,y,maxWidth);
		 * 注意！！！y是文字的最底部的值，并不是top的值！！！
		 * */
		
		
		
		if(rewardName.indexOf("M") > 0) { //查询是否包含字段 流量包
			var rewardNames = rewardName.split("M");
			for(var j = 0; j < rewardNames.length; j++) {
				ctx.font = (j == 0) ? 'bold 20px Microsoft YaHei' : '16px Microsoft YaHei';
				if(j == 0) {
					ctx.fillText(rewardNames[j] + "M", -ctx.measureText(rewardNames[j] + "M").width / 2, j * line_height);
				} else {
					ctx.fillText(rewardNames[j], -ctx.measureText(rewardNames[j]).width / 2, j * line_height);
				}
			}
		} else if(rewardName.indexOf("M") == -1 && rewardName.length > 6) { //奖品名称长度超过一定范围
			rewardName = rewardName.substring(0, 6) + "||" + rewardName.substring(6);
			var rewardNames = rewardName.split("||");
			for(var j = 0; j < rewardNames.length; j++) {
				ctx.fillText(rewardNames[j], -ctx.measureText(rewardNames[j]).width / 2, j * line_height);
			}
		} else {
			//在画布上绘制填色的文本。文本的默认颜色是黑色
			ctx.fillText(rewardName, -ctx.measureText(rewardName).width / 2, 0);
		}

		//还原画板的状态到上一个save()状态之前
		ctx.restore();
		/*----绘制奖品结束----*/

	}
}

//drawWheelCanvas();
var hudongModule = {};
hudongModule.cardFlag = false;
hudongModule.currentCard = {
	top: 0,
	left: 0,
	dom: null
};
hudongModule.status = 0;

hudongModule.user =[];
hudongModule.luckyuser =[];

hudongModule.showBtn = function(){
	if(this.status == 0){
		$('#hudong-start').show();	
	}else{
		$('#hudong-back').show();	
	}
}


function cardClick() {
	var that = this;
	var _w = this.offsetLeft;
	var _h = this.offsetTop;
	console.log('click=' + _h);
	console.log(_w);

	hudongModule.currentCard.top = _h;
	hudongModule.currentCard.left = _w;
	hudongModule.currentCard.dom = this;

	$(this).siblings().addClass('dim');

	$(this).animate({ 'left': "460px", 'top': _h + 30 + 'px' }, 500, function() {
		console.log($(that).find('div'));
		$(that).find('div').animate({ "zoom": "1.5" }, 800, function() {
			if($(that).find('div').hasClass('flipped')) {
				$(that).find('div').removeClass('flipped').addClass('return');
			} else {
				$(that).find('div').removeClass('return').addClass('flipped');
			}
		})
	})
}

$('.card-warp li').on('click', function(e) {
	//alert(111);
	console.log('当前动画：' + $(hudongModule.currentCard.dom).is(":animated"))
	//$('#card-block').show();
	if(!hudongModule.cardFlag) {
		hudongModule.cardFlag = true;
		var that = this;
		var _w = this.offsetLeft;
		var _h = this.offsetTop;
		console.log('click=' + _h);
		console.log(_w);

		hudongModule.currentCard.top = _h;
		hudongModule.currentCard.left = _w;
		hudongModule.currentCard.dom = this;

		$(this).addClass('disabled')

		$(this).siblings().addClass('dim disabled');

		$(hudongModule.currentCard.dom).animate({ 'left': "460px", 'top': _h + 30 + 'px' }, 500, function() {
			console.log($(that).find('div'));
			$(hudongModule.currentCard.dom).find('div').animate({ "zoom": "1.5" }, 800, function() {
				if($(hudongModule.currentCard.dom).find('div').hasClass('flipped')) {
					$(hudongModule.currentCard.dom).find('div').removeClass('flipped').addClass('return');
				} else {
					$(hudongModule.currentCard.dom).find('div').removeClass('return').addClass('flipped');
				}
				$(hudongModule.currentCard.dom).removeClass('disabled');
			})
		})
	} else {
		hudongModule.cardFlag = false;
		$(hudongModule.currentCard.dom).addClass('disabled');

		$(hudongModule.currentCard.dom).find('div').animate({ "zoom": "1" }, 800, function() {
			if($(hudongModule.currentCard.dom).find('div').hasClass('flipped')) {
				$(hudongModule.currentCard.dom).find('div').removeClass('flipped').addClass('return');
			} else {
				$(hudongModule.currentCard.dom).find('div').removeClass('return').addClass('flipped');
			}

			$(hudongModule.currentCard.dom).animate({ 'left': hudongModule.currentCard.left + 'px', 'top': hudongModule.currentCard.top + 'px' }, 500, function() {
				$(hudongModule.currentCard.dom).siblings().removeClass('dim disabled');
				$(hudongModule.currentCard.dom).removeClass('disabled');
			})
		})

	}

})

var testUser = [];
var cardTimer;

//抽取人数 逻辑
for(var i = 0; i < 100; i++) {
	var index = Math.round(Math.random() * 5 + 1);
	testUser.push({
		'headimg': 'img/headimg/head' + index + '.png',
		'name': '胡小林' + i
	})
}

$('#hudong-start').on('click', function() {
	hudongModule.status = 1; //进入翻牌子阶段
	$(this).hide();
	$('#hudong-back').show();
	$('.leda1').hide();
	$('.card-warp').show();
	$('.card-warp li').each(function(i, item) {
		if(i % 2 == 0) {
			console.log(item.offsetLeft - Math.ceil(i / 2) * 250 + 'px');
			$(item).animate({ "left": item.offsetLeft - Math.ceil(i / 2) * 250 + 'px' }, 300, "linear", function() {})

		} else {
			$(item).animate({ "left": item.offsetLeft + Math.ceil(i / 2) * 250 + 'px' }, 300, "linear", function() {})
		}
	})
})


$('#hudong-back').on('click',function(){
	hudongModule.status = 0; //进入抽人阶段
	$(this).hide();
	$('#hudong-start').show();
	$('.leda1').show();
	$('.card-warp').hide();
	$('.card-warp li').css('left','520px')
		
})

function getcarduser() {
	var index = randomNum(0, hudongModule.user.length );
	var index1 = randomNum(0, hudongModule.user.length );
	$('#hudonguser1-img').attr('src',pageInfo.picHost+ hudongModule.user[index].reheadimgurl)
	$('#hudonguser1').html(hudongModule.user[index].renickname);

	$('#hudonguser2-img').attr('src',pageInfo.picHost+hudongModule.user[index1].reheadimgurl)
	$('#hudonguser2').html(hudongModule.user[index1].renickname);

//	console.log(testUser[index1].renickname)

}

$('#btn-peidui').on('click', function() {
	
	if(hudongModule.user.length<3){
		layer.msg("互动游戏人数不够哦")
		return;
	}
	
	$('#btn-peidui').addClass('disabled')
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})
	
	//console.log(testUser);
	

	
	
	cardTimer = setInterval(getcarduser, 50);

	setTimeout(function() {

		var index = randomNum(0, hudongModule.user.length);
		var index1 = randomNum(0, hudongModule.user.length);

//		console.log("当前用户的数量:"+hudongModule.user.length);
		while(1) {
			if(index == index1) {
				index1 = randomNum(0, hudongModule.user.length);
			} else {
				break;
			}
		}
		
//		if(index == index1){
//			console.log( "type of = " + typeof(hudongModule.user[index+1]));
//			console.log( "type of = " + typeof(hudongModule.user[index-1]));
//			if(typeof(hudongModule.user[index+1]!='undefined')){
//				index1 +=1;
//			}else{
//				index1 -=1;
//			}
//			
//		}
		
//		console.log('下标1=' +index);
//		console.log('下标2='+index1);
		
		var user1 = hudongModule.user[index].reheadimgurl;
		var user2 = hudongModule.user[index1].reheadimgurl;
		clearInterval(cardTimer);

		$('#hudonguser1-img').attr('src', pageInfo.picHost+hudongModule.user[index].reheadimgurl)
		$('#hudonguser1').html(hudongModule.user[index].renickname);

		$('#hudonguser2-img').attr('src', pageInfo.picHost+hudongModule.user[index1].reheadimgurl)
		$('#hudonguser2').html(hudongModule.user[index1].renickname);

		$('#btn-peidui').removeClass('disabled');

//		console.log('下标对象1=');
//		console.log(hudongModule.user[index]);
//		console.log('下标对象2=');
//		console.log(hudongModule.user[index1]);
		putzuhe(hudongModule.user[index].reheadimgurl, hudongModule.user[index1].reheadimgurl, hudongModule.user[index].renickname, hudongModule.user[index1].renickname)
			
		//var a =	hudongModule.user.splice(index+1, 1);
		//var b =	hudongModule.user.splice(index1+1, 1);
		
		removeByValue(hudongModule.user,user1);
		
		removeByValue(hudongModule.user,user2);
		
//		console.log(user1);
//		console.log(user2);
//		console.log('remove user='+index);
//		console.log('remove user='+index1);
//		console.log(hudongModule.user.length);

	}, 1000)

	//				$('.leda1').snabbt({
	//					opacity:0.1,
	//					duration: 50,	
	//					easing: 'ease'
	//				})

	//				$('.leda1').hide();
	//				$('.card-warp').show();

	//				$('.card-warp li').animate({"left":"100px"},2000);
	//				return;

	/*	snabbt(document.querySelectorAll('.card-warp li'), {
			fromRotation: [0, 0, 0],
			position: function(i, total) {
				console.log('i=' + i)
				console.log('total=' + total)

				if(i % 2 == 0) {
					return [-Math.floor(i / 2) * 250, 0, 0];
				} else {
					return [Math.ceil(i / 2) * 250, 0, 0];
				}

			},
			delay: function(i) {
				//return i * 500;
				return 100;
			},
			//					easing: 'spring',
		}).snabbt({
			rotation: [0, 0, 0],
			delay: function(i, total) {
				return(total - i - 1) * 50;
			},
			easing: 'ease',
		});*/

})

function putzuhe(img1, img2, name1, name2) {
	$('.peiduizuhe').not('.mlucky').each(function(i, item) {
		if(!$(item).hasClass('done')) {
			$(item).addClass('done');
			item.children[1].src = pageInfo.picHost+ img1;
			item.children[3].src = pageInfo.picHost+img2;
			item.children[4].innerHTML = name1;
			item.children[5].innerHTML = name2;
			return false;
		}
	})
}



$('#btn-peidui-rest').on('click', function() {
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})

	$('.peiduizuhe').not('.mlucky').each(function(i, item) {
		$(item).removeClass('done');
		item.children[1].src = pageInfo.path+'/static/resource/img/abc_03.png';
		item.children[3].src = pageInfo.path+'/static/resource/img/abc_03.png';
		item.children[4].innerHTML = '';
		item.children[5].innerHTML = '';
	})
	
	hudongModule.user.length = 0;
	hudongModule.user =simpleCopy(userJoinedArr);
})





var luckyTimer;


function setLuckyuserImg(){
	var index = randomNum(0, hudongModule.luckyuser.length);
	$('#luckyuser-img').attr('src',pageInfo.picHost+ hudongModule.luckyuser[index].reheadimgurl);
	$('#luckyuser-name').html(hudongModule.luckyuser[index].renickname);
	
}

$('#btn-lucky-start').on('click',function(){
	if(hudongModule.luckyuser.length<1){
		layer.msg("互动游戏人数不够哦")
		return;
	}
	
	$(this).addClass('disabled')
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})
	
	luckyTimer = setInterval(setLuckyuserImg,50);
	
	setTimeout(function(){
		clearInterval(luckyTimer);
		
		var index = randomNum(0, hudongModule.luckyuser.length);
	
		$('#luckyuser-img').attr('src',pageInfo.picHost+hudongModule.luckyuser[index].reheadimgurl);
		$('#luckyuser-name').html(hudongModule.luckyuser[index].renickname);
		
		$('#luckyuser-warp').append('<div class="lucky-item"><img src="'+pageInfo.picHost+hudongModule.luckyuser[index].reheadimgurl+'" style="width: 60px;height: 60px;border-radius: 50%;margin: 0;" /><span class="lucky-username">'+hudongModule.luckyuser[index].renickname+'</span></div>')
		
		hudongModule.luckyuser.splice(index, 1);
		
		$('#btn-lucky-start').removeClass('disabled');
		
	},1500)
	
})

$('#btn-lucky-rest').on('click',function(){
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})
	$('#luckyuser-warp').empty();
	
	hudongModule.luckyuser.length = 0;
	hudongModule.luckyuser=simpleCopy(userJoinedArr);
	
})

//删除对象
function removeByValue(arr, val) {
  for(var i=0; i<arr.length; i++) {
    if(arr[i].reheadimgurl == val) {
      arr.splice(i, 1);
      break;
    }
  }
}












//背景音乐
var normalbgm = document.createElement("audio");

// 音效
var soundef = document.createElement("audio");

// 特殊背景音乐
var specialbgm = document.createElement("audio");


//var bgmList = [ 'bgm/01 - My Love.mp3', 'bgm/01 - Only Love.mp3',
//		'bgm/02 - If I Let You Go.mp3', 'bgm/09 - Seasons In The Sun.mp3',
//		'bgm/A little love - 馮曦妤.mp3', 'bgm/Blue - One Love.mp3',
//		'bgm/Boyzone - No Matter What.mp3', 'bgm/Bruno Mars - Marry You.mp3',
//		"bgm/Darin - Can't Stop Love.mp3", 'bgm/I’m Yours - Naia Kete.mp3',
//		'bgm/Lovestoned - Bye Bye Bye.mp3', 'bgm/Lovestoned - Invitation.mp3',
//		'bgm/Lovestoned - Rising Girl.mp3',
//		"bgm/Lovestoned - Who's That Girl.mp3",
//		'bgm/M2M - Girl In Your Dreams.mp3', 'bgm/M2M - Pretty Boy.mp3',
//		'bgm/Proud Of You - 冯曦妤.mp3',
//		'bgm/Rod Stewart - For The First Time.mp3',
//		'bgm/Victoria Acosta - Could This Be Love.mp3',
//		'bgm/冯曦妤 - Find Your Love.mp3', 'bgm/张娜拉 - Sweet Dream.mp3' ];
var bgmList =['bgm/01- - My Love.mp3'];

var MUSIC_DRAW = pageInfo.path + '/static/resource/soundef/bgm_draw.mp3';
var MUSIC_SHAKE = pageInfo.path + '/static/resource/soundef/bgm_shake.mp3';
var MUSIC_VOTE = pageInfo.path + '/static/resource/soundef/bgm_vote.mp3';
var MUSIC_GUESS = pageInfo.path + '/static/resource/soundef/guess.mp3';

var MUSIC_MONEY_1 =  pageInfo.path + '/static/resource/soundef/money1.mp3';
var MUSIC_MONEY_2 =  pageInfo.path + '/static/resource/soundef/money3.mp3';

var MUSIC_HUADENG =  pageInfo.path + '/static/resource/soundef/huadeng.mp3';


for (var i = 0; i < bgmList.length; i++) {
	bgmList[i] = pageInfo.path + "/static/resource/" + bgmList[i];
}

// 获取背景音乐列表

var soundefList = [ "soundef/bgm_draw.mp3", "soundef/bgm_shake.mp3",
		"soundef/bgm_vote.mp3" ];

var testmsList = [ "testms/Button32.wav", "testms/click_01.wav",
		"testms/giftcome.mp3" ]

var bgm_current = 0;

//normalbgm.src = bgmList[bgm_current];
//normalbgm.play();

// setTimeout('normalbgm.pause()',3000);

normalbgm.addEventListener('ended', function() {
	if (bgm_current < bgmList.length - 1) {
		bgm_current++;
	} else {
		bgm_current = 0;
	}
	normalbgm.src = bgmList[bgm_current];
	normalbgm.play();
})


function pauseSpecialbgm(){
	specialbgm.pause();
}

//
function playSpecialbgm(src) {
	if (!src) {
		// changeVolume(bgm,1,'up');
		specialbgm.pause();
		normalbgm.play();
	
	} else {
		normalbgm.pause();
		// changeVolume(bgm,0.1,'down');
		specialbgm.src = src;
		specialbgm.loop = 'loop';
		specialbgm.play();
	}

}

function changeVolume(v, dest, isDown) {
	setTimeout(function() {
		if (isDown == 'up') {
			if (v.volume >= dest) {
				v.volume -= 0.02;
				changeVolume(v, dest, isDown);
			}
		} else if (isDown == 'down') {
			if (v.volume <= dest) {
				v.volume += 0.02;
				changeVolume(v, dest, isDown);
			}
		}
	}, 50)
}

function toogleMusic(obj) {
	if (normalbgm.paused && !specialbgm.paused) {
		specialbgm.pause();
		obj.dataset.current = '1';
		obj.children[0].src = pageInfo.path
				+ '/static/resource/img/index/yinyueclose.png';
	} else if (!normalbgm.paused && specialbgm.paused) {
		normalbgm.pause();
		obj.dataset.current = '0';
		obj.children[0].src = pageInfo.path
				+ '/static/resource/img/index/yinyueclose.png';
	} else if (normalbgm.paused && specialbgm.paused) {
		obj.children[0].src = pageInfo.path
				+ '/static/resource/img/index/yingyue.png';
		if (obj.getAttribute('data-current') == '0') {
			normalbgm.play();
		} else {
			specialbgm.play();
		}
	}
}

/*
 * var soundModule = { audioPlayer: {}, bgmList: [], specialbgmList: [],
 * current: 0,
 * 
 * init: function(obj) { var that = this;
 * 
 * that.current = 0; //背景音乐 var a = document.createElement("audio"); //音效 var b =
 * document.createElement("audio"); //特殊背景音乐 var c =
 * document.createElement("audio");
 * 
 * audioPlayer.bgm = { 'player': a, 'loop': true }; audioPlayer.specialbgm = {
 * 'player': b }; a.src = bgmList; a.addEventListener('ended', function() {
 * if(soundModule.current < soundModule.bgmList.length - 1) {
 * soundModule.current++; this.src = soundModule.bgmList[soundModule.current];
 * this.play(); } else { soundModule.current = 0; this.src =
 * soundModule.bgmList[soundModule.current]; this.play(); } });
 * specialbgmList.push('soundef/bgm_draw.mp3');
 * specialbgmList.push('soundef/bgm_shake.mp3');
 * specialbgmList.push('soundef/bgm_vote.mp3'); } }
 */var userJoinedArr = [];

var needRefresh = false;

$(document).ready(function() {
	bindEvent();
	//	window.onresize = resizeHtml;
	//	setTimeout(resizeHtml, 200);

	getPeople(true);

	$('#gameErweima').attr('src', pageInfo.qrcodeUrl);

	drawWheelCanvas(); //渲染转盘

	getChatLocal();
	//getGXB();

	$('.w-people-warp-l div').empty();
	$('.w-people-warp-r div').empty();
})

var myWebsocket;

var rank_layer;
var rank_result_layer;

var gamePointInterval; //获取游戏分数

var giftInfo = {};
giftInfo.queue = [];
giftInfo.status = false;
var giftTemp = ["bike3.gif", "kiss.gif", "love.gif", "dance.gif", "zaosheng.gif"]; // 礼物

var gameInfo = {
	gameIsRuning: false, // 游戏是否运行中
	currentGame: null, // 当前游戏
	manList: [], // 当前参与游戏人数
	blueTeam: [], //蓝队人数
	redTeam: [], //红队人数
	shakeGame: {
		runInterval: null,
		getpointInterval: null,
		progressTotal: 1000,
		init: function() {
			// 初始化摇一摇游戏
			$('.kiss').hide();
			$('.kiss').removeClass('kiss-ani');
			$('#man-run').removeClass('husband-run-x');
			$('.num').removeClass('count-down');
			$('.tip-box').hide();
			$('.husband').show();
			$('.wife').show();

			$('.front').addClass('move1');
			$('.back').addClass('move2');
			$('.boat1').addClass('move3');
			$('#game-ready').removeClass('game-ready');
			$('.progress-bar').css('height', '10px')
			$('.user').attr('src', pageInfo.path + '/static/resource/img/game/abc_03.png');
			$('.username').html('Payer');
			$('.userpoint').html('0')
		}
	}
}

var _sign3D = null;

function bindEvent() {

	$('#btn_fullsc').on('click', fullSc);

	$('#btn_cw').on('click', toogleCW);

	document.getElementById("bigCard").addEventListener('webkitAnimationEnd', function() {
		this.classList.remove('signani');
		setTimeout(sign_usercome_3d, 1000);
	});

	$('.damu-switch').on('click', function(e) {
		danmuModule.danmuSwitch();
		if($('.screen-gift-warp').is(":visible")) {
			$('.screen-gift-warp').hide();
			$(this).find('img').attr('src', pageInfo.path + 'static/resource/img/index/danmuclose.png');
		} else {
			$('.screen-gift-warp').show();
			$(this).find('img').attr('src', pageInfo.path + 'static/resource/img/index/danmu.png');
		}
	});

	$('.btn-module').on('click', function(e) {
		//danmuModule.postDanmu('http://wx.qlogo.cn/mmopen/ajNVdqHZLLAv5QEExV4M05bfulngA3Q6mR71N2gbnJQ96yN3XoziaricFHhxEVKWhELIumF6kM1Ys1S6pdiaCiaUuQ/0','ccc','祝新人早生贵子')
		if($(this).hasClass('this'))
			return;
		if(gameInfo.gameIsRuning) {
			layer.msg('游戏进行中.请先关闭游戏哦');
			return;
		}

		$('.btn-menu-big').hide();
		$('.module').removeClass('active');
		$('.module').removeClass('fadeIn');
		$('.module').hide();
		$(this).addClass('this').siblings().removeClass('this');
		var id = $(this).attr('data-des');
		$('#' + id).addClass('active fadeIn');
		$('#' + id).show();
		switch(id) {
			case 'm-3dsign':
				//playSpecialbgm();
				showCW(); //显示聊天框
				//fullSc.call($('#btn_fullsc')[0])
				requestFullScreen(document.documentElement);
				$('#btn_fullsc').addClass('done');

				if(!erweimaLayer) {
					erweimaLayer = layer.open({
						type: 1,
						title: '',
						area: [400 * pageInfo.scale + 'px', 400 * pageInfo.scale + 'px'], //宽高
						content: '<img src="' + pageInfo.qrcodeUrl + '" style="height:100%;width:100%"/>',
						shade: 0,
						move: '.layui-layer-content',
						offset: 'r',
						end: function() {
							erweimaLayer = null
						}
					});
				}

				pauseSpecialbgm(); //关闭音乐
				if(gameInfo.currentGame != 'sign') {
					var loading = layer.msg('加载中', {
						icon: 16,
						shade: 0.01,
						time: 0
					});

					$.get(pageInfo.postUrl + "game/initGame", {
						gameCode: 'sign',
						liveId: pageInfo.roomId
					}, function(result) {
						layer.close(loading);
						var _joinPlayers = [];
						if(!_sign3D) {
							var map = null;
							_sign3D = new SingninCls($('.signin3D'), _joinPlayers, map);
							_sign3D.show();
							//add joined user
							//							userJoinedArr.push({
							//								headimg: "http://wx.qlogo.cn/mmopen/ajNVdqHZLLAv5QEExV4M05bfulngA3Q6mR71N2gbnJQ96yN3XoziaricFHhxEVKWhELIumF6kM1Ys1S6pdiaCiaUuQ/0",
							//								username: "胡小林",
							//								wish: "来吧来吧"
							//							});

							if(userJoinedArr.length == 0) {
								$.get(gameHost + "/reportController/report/" + pageInfo.roomId, function(data) {
									$('#current-people').html(data.data.length + '');
									userJoinedArr = data.data;
									$('.card').find('img').attr('onerror', 'errorImg(this)');
									$('.bigCard').find('img').attr('onerror', 'errorImg(this)');
									putuser_3d(userJoinedArr);

								}, 'json').error(function() {
									$('.card').find('img').attr('onerror', 'errorImg(this)');
									$('.bigCard').find('img').attr('onerror', 'errorImg(this)');
									putuser_3d(userJoinedArr);
								})
							} else {
								putuser_3d(userJoinedArr);
								$('.card').find('img').attr('onerror', 'errorImg(this)');
								$('.bigCard').find('img').attr('onerror', 'errorImg(this)');

							}
							//putuser_3d(userJoinedArr);
						}

						gameInfo.currentGame = 'sign'; //当前状态激活为签到
					}).error(function() {
						layer.close(loading);
						layer.msg("出错了,请重试");
					})
				}

				break;

			case 'm-sign':

				break;

			case 'm-bahe-wait':
				playSpecialbgm(MUSIC_SHAKE);

				hideCW(); //隐藏聊天框
				//$('#btn-bahe').removeClass('this');
				//if(gameInfo.currentGame =='hdGame12'&& $('#m-bahe-wait').hasClass('active'))
				//	return ;
				gameInfo.currentGame = 'hdGame12';
				var loading = layer.msg('加载中', {
					icon: 16,
					shade: 0.01,
					time: 0
				});

				$.get(pageInfo.postUrl + "game/initGame", {
					gameCode: gameInfo.currentGame,
					liveId: pageInfo.roomId
				}, function(result) {
					layer.close(loading);
					//getGameInfo("hdGame11");
					initGameBh();
				}).error(function() {
					layer.close(loading);
					layer.msg('出错了 请重试');
				})
				break;

			case 'm-shakegame':
				hideCW(); //隐藏聊天框
				playSpecialbgm(MUSIC_SHAKE);
				// init摇一摇游戏
				gameInfo.currentGame = 'hdGame11';
				var loading = layer.msg('加载中', {
					icon: 16,
					shade: 0.01,
					time: 0
				});
				//alert(pageInfo.postUrl + "game/initGame")
				$.get(pageInfo.postUrl + "game/initGame", {
					gameCode: gameInfo.currentGame,
					liveId: pageInfo.roomId
				}, function(result) {
					layer.close(loading);
					getGameInfo("hdGame11");
				}).error(function() {
					layer.close(loading);
					layer.msg('出错了 请重试');
				})
				break;
			case 'm-lottery':
				hideCW(); //隐藏聊天框
				playSpecialbgm(MUSIC_DRAW);
				//$('#lottery-rest').show();	

				initLottery(false);
				break;

			case 'm-dzp':
				hideCW(); //隐藏聊天框
				pauseSpecialbgm(); //关闭音乐
				//playSpecialbgm(MUSIC_DRAW);

				//layer.msg('zoom');

				//				if(!pageInfo.needResize){
				//					$('#m-dzp .need-zoom').css('zoom',pageInfo.zoom);
				//				}

				break;

			case 'm-vote':
				hideCW(); //隐藏聊天框
				//playSpecialbgm(MUSIC_VOTE);

				//				if(!pageInfo.needResize){
				//					$('#m-vote .need-zoom').css('zoom',pageInfo.zoom);
				//				}

				//初始化为投票
				$.get(pageInfo.postUrl + "game/initGame", {
					gameCode: 'vote',
					liveId: pageInfo.roomId
				}, function(result) {
					voteModule.init();
					gameInfo.currentGame = 'vote';
				}).error(function(e) {
					layer.msg('投票开启失败,请重置')
				})
				$('#vote-rest').show()
				//layer.msg('zoom');
				break;

			case 'm-hudong':
				hideCW(); //隐藏聊天框
				pauseSpecialbgm();
				hudongModule.showBtn();
				//				if(!pageInfo.needResize){
				//					$('#m-hudong .need-zoom').css('zoom',pageInfo.zoom);
				//				}
				if(needRefresh) {
					getPeople(true);
					needRefresh = false;
				}

				//				layer.msg('zoom');
				break;

			case 'm-video':
				hideCW(); //隐藏聊天框
				//playSpecialbgm();
				//toogleMusic($('#toogleMusic')[0]);
				//				if(!pageInfo.needResize){
				//					$('#m-video .need-zoom').css('zoom',pageInfo.zoom);
				//				}
				//setTimeout(videoModule.startAni,1000)
				//				videoModule.startAni();
				break;
			case 'm-luckyman':
				hideCW(); //隐藏聊天框
				//playSpecialbgm();
				pauseSpecialbgm()
				//				if(!pageInfo.needResize){
				//					$('#m-luckyman .need-zoom').css('zoom',pageInfo.zoom);
				//				}
				if(needRefresh) {
					getPeople(true);
					needRefresh = false;
				}

				break;

			case 'm-main':
				hideCW(); //隐藏聊天框
				pauseSpecialbgm();
				break;

			case 'm-guess':
				hideCW(); //隐藏聊天框
				playSpecialbgm(MUSIC_GUESS);
				break;

			case 'm-sing':
				toogleMusic($('#toogleMusic')[0]);
				//				alert('main');
				break;
			case 'm-money':
				//数钱游戏
				playSpecialbgm(MUSIC_MONEY_1);

				hideCW(); //隐藏聊天框

				var gid = 'hdGame13'

				getGameInfo(gid);

				var loading = layer.msg('加载中', {
					icon: 16,
					shade: 0.01,
					time: 0
				});

				$.get(pageInfo.postUrl + "game/initGame", {
					gameCode: gameInfo.currentGame,
					liveId: pageInfo.roomId
				}, function(result) {
					layer.close(loading);
				}).error(function(e) {
					layer.msg('网络异常，请重试');
				})

				break;
			default:
				break;
		}

	})

	$('.game-menu ul li').on('click', function(e) {
		//		layer.msg('游戏玩起来');
		//		$('.game-menu').addClass('hide');
		//		$('.game-wait').show();
	})

	// 选择游戏
	$('#youxi').on('click', function(e) {
		if(gameInfo.gameIsRuning) {
			layer.msg('游戏进行中.请先关闭游戏哦');
			return
		}

		var tempPath = 'http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/game/';
		// 页面层
		var youxi_layer = layer.open({
			type: 1,
			title: '',
			skin: 'layui-layer-rim', // 加上边框
			area: ['65%', '35%'], // 宽高
			content: '<div class="game-menu" style=""><ul><li data-id="hdGame1"><img src="' + tempPath + 'hdGame1.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/5.png"></li><li data-id="hdGame2"><img src="' + tempPath + 'hdGame2.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/8.png"></li><li data-id="hdGame3" style="display:none"><img src="' + pageInfo.path + '/static/resource/img/game/hdGame3.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/1.png"></li><li data-id="hdGame4" style="display:none"><img src="' + pageInfo.path + '/static/resource/img/game/hdGame4.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/2.png"></li><li data-id="hdGame5"><img src="' + tempPath + 'hdGame5.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/3.png"></li><li data-id="hdGame6" style="display:none"><img src="' + pageInfo.path + '/static/resource/img/game/hdGame6.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/4.png"></li><li data-id="hdGame7" style="display:none"><img src="' + pageInfo.path + '/static/resource/img/game/hdGame7.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/6.png"></li><li data-id="hdGame8"><img src="' + tempPath + 'hdGame8.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/7.png"></li><li data-id="hdGame9"><img src="' + tempPath + 'hdGame9.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/9.png"></li></ul></div>',
			// move: '.layui-layer-content',
			shadeClose: 'true',
			resize: 'false'
		});

		if(pageInfo.zoom < 1 || pageInfo.needResize) {
			layer.style(youxi_layer, {
				top: '250px',
				left: '200px',
				background: 'rgba(255,255,255,0)',
				border: 'none'
			});
		} else {
			layer.style(youxi_layer, {
				background: 'rgba(255,255,255,0)',
				border: 'none'
			});
		}

		$('.game-menu ul li').on('click', function(e) {

			playSpecialbgm(MUSIC_VOTE);
			hideCW();

			var loading = layer.msg('加载中', {
				icon: 16,
				shade: 0.01,
				time: 0
			});
			var gameID = $(this).data('id');
			$('.btn-menu-big').hide();
			$.get(pageInfo.postUrl + "game/initGame", {
				gameCode: gameID,
				liveId: pageInfo.roomId
			}, function(result) {
				//playSpecialbgm();
				layer.closeAll();

				//				if(pageInfo.zoom<1||pageInfo.needResize){
				//					$('.leda').css('margin-top', '0px');
				//					$('#erweima').css('margin-top', '0px');
				//				}else{
				//					$('.leda').css('margin-top', (window.innerHeight - 140 - $('.leda').height()) / 2 + 'px');
				//					$('#erweima').css('margin-top', (window.innerHeight - 140 - $('.leda').height()) / 2 + $('.game-wait-people').height() + 'px');
				//				}
				$('.module').removeClass('active');
				$('.module').removeClass('fadeIn');
				$('#youxi').addClass('this').siblings().removeClass('this');
				var id = $('#youxi').attr('data-des');
				$('#' + id).addClass('active fadeIn');

				$('#game-start').show();

				getGameInfo(gameID);
			}).error(function() {
				layer.closeAll();
				layer.msg('请重新选择游戏');
			});
		})
	})

	// 游戏开始
	$('#game-start').on('click', function(e) {
		//TODO
		if(gameInfo.currentGame == 'hdGame12') {
			//拔河游戏 TODO
			var loading = layer.msg('加载中...', {
				icon: 16,
				shade: 0.01,
				time: 0
			});

			$.get(pageInfo.postUrl + "game/beginGame", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {
				layer.close(loading);
				//进行拔河游戏  TODO
				$('#game-start').hide();
				//$('#game-end').show();
				$('.module').removeClass('active');
				$('.module').removeClass('fadeIn');
				gameInfo.gameIsRuning = true;
				$('#m-bahe').show();
				$('#m-bahe').addClass('active fadeIn');

				startBaheGame();

			}).error(function() {
				layer.close(loading);
				layer.msg('出错了,请重试')
			})

		} else if(gameInfo.currentGame == 'hdGame11') {
			//			var loading = layer.msg('加载排名中...', {
			//				icon: 16,
			//				shade: 0.01,
			//				time: 0
			//			});

			gameInfo.shakeGame.init();
			//			
			//				layer.close(loading);
			$('.btn-menu-big').hide();

			$('.module').removeClass('active');
			$('.module').removeClass('fadeIn');
			$('.module').hide();
			$('.shakegame').addClass('active');
			$('.shakegame').addClass('fadeIn');
			$('.shakegame').show();

			$('#game-start').hide()
			//$('#game-rest').show();

			$('.tip-box-start').addClass('game-ready');
			$('.tip-box-start').show();
			gameInfo.gameIsRuning = true;

		} else if(gameInfo.currentGame == 'hdGame13') {
			//数钱游戏
			$('.module').removeClass('active');
			$('.module').removeClass('fadeIn');
			$('.module').hide();
			$('#m-money').addClass('active');
			$('#m-money').addClass('fadeIn');
			$('#m-money').show();
			$('#game-start').hide();

			//			var loading = layer.msg('游戏加载中...', {
			//				icon: 16,
			//				shade: 0.01,
			//				time: 0
			//			});

			moneyGame.init();
			moneyGame.start();

			setTimeout(function() {

				$.get(pageInfo.postUrl + "game/beginGame", {
					gameCode: gameInfo.currentGame,
					liveId: pageInfo.roomId
				}, function(result) {

				}).error(function() {

				})
			}, 4500);

		} else if(gameInfo.currentGame == 'hdGame1') {
			//载入点花灯画面
			var loading = layer.msg('加载中...', {
				icon: 16,
				shade: 0.01,
				time: 0
			});

			$.get(pageInfo.postUrl + "game/beginGame", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {

				layer.close(loading);

				$('.btn-menu-big').hide();
				$('.module').removeClass('active').removeClass('fadeIn').hide();
				$('#m-huadeng').addClass('active fadeIn').show();;

				$('#game-end').show();

				huadengGame.start();
			}).error(function() {
				layer.close(loading);
				layer.msg("网络异常,请重试");
			})

		} else {
			//小游戏

			var loading = layer.msg('加载排名中...', {
				icon: 16,
				shade: 0.01,
				time: 0
			});

			$.get(pageInfo.postUrl + "game/beginGame", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {
				layer.closeAll();
				// 开始请求游戏排名
				startMobileGame();

				//打开倒计时
				toogleCountDown();

				var s = pageInfo.x ? 'transform: translateX(' + pageInfo.x + 'px)' : 'transform: translateY(' + pageInfo.y + 'px)';

				// 打开排名 layer
				rank_layer = layer.open({
					type: 1,
					title: '',
					skin: 'layui-layer-rim', // 加上边框
					area: ['110%', '80%'], // 宽高
					content: '<div class="game-rank" id="game-rank" style="left:20px;zoom:' + pageInfo.scale + ';' + s + '"><div class="rank-item ani_flipInX ani_delay1"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span><img src="' + pageInfo.path + '/static/resource/img/game/paiming_06.png" class="jiangbei"></div><div class="rank-item ani_flipInX ani_delay2"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span><img src="' + pageInfo.path + '/static/resource/img/game/paiming_13.png" class="jiangbei"></div><div class="rank-item ani_flipInX ani_delay3"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span><img src="' + pageInfo.path + '/static/resource/img/game/paiming_17.png" class="jiangbei"></div><div class="rank-item ani_flipInX ani_delay4"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_21.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span></div><div class="rank-item ani_flipInX ani_delay5"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_27.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span></div><div class="rank-item ani_flipInX ani_delay6"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_34.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span></div></div>',
					shade: 0,
					// move: '.layui-layer-content',
					// shadeClose: 'true',
					resize: 'false'
				});

				//				if(pageInfo.zoom<1||pageInfo.needResize){
				//					layer.style(rank_layer, {
				//						background: 'rgba(255,255,255,0)',
				//						border: 'none',
				//						left: '0',
				//						top:'70px'
				//					});
				//				}else{
				//					layer.style(rank_layer, {
				//						background: 'rgba(255,255,255,0)',
				//						border: 'none',
				//						left: '0'
				//					});
				//				}

				layer.style(rank_layer, {
					background: 'rgba(255,255,255,0)',
					border: 'none',
					left: '0'
				});

				$('#game-start').hide();
				$('#game-end').show();

				$('.module').removeClass('active');
				$('.module').removeClass('fadeIn');

				gameInfo.gameIsRuning = true;
			}).error(function() {
				layer.close(loading);
				layer.msg('出错了,请重试')
			})

		}
	})

	// 游戏结束
	$('#game-end').on('click', function(e) {

		if(gameInfo.currentGame == 'hdGame1') {

			clearInterval(huadengGame.aniInterval);
			clearInterval(huadengGame.rankInterval);

			huadengGame.end();

			return;
		}

		var loading = layer.msg('结束中...', {
			icon: 16,
			shade: 0.01,
			time: 0
		});

		if($('#mgamecountdown').is(":visible")) {
			toogleCountDown();
		}

		var s = pageInfo.x ? 'transform: translateX(' + pageInfo.x + 'px)' : 'transform: translateY(' + pageInfo.y + 'px)';

		$.get(pageInfo.postUrl + 'game/endGame', {
			liveId: pageInfo.roomId,
			gameCode: gameInfo.currentGame
		}, function(data) {
			layer.closeAll();
			$("#game-end").hide();
			clearInterval(gamePointInterval);
			rank_layer = layer.open({
				type: 1,
				closeBtn: '0',
				title: '',
				skin: 'layui-layer-rim', // 加上边框
				area: ['40%', '70%'], // 宽高
				content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_09.png"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
				// move: '.layui-layer-content',
				shadeClose: 'true',
				resize: 'false',
			});

			gameInfo.gameIsRuning = false;

			//			if(pageInfo.zoom<1||pageInfo.needResize){
			//				layer.style(rank_layer, {
			//					background: 'rgba(255,255,255,0)',
			//					border: 'none',
			//					top:'100px',
			//					left:'400px',
			//					scrollbar:false,
			//					height:'550px'
			//				});
			//			}else{
			//				layer.style(rank_layer, {
			//					background: 'rgba(255,255,255,0)',
			//					border: 'none',
			//					height:'550px'
			//				});
			//			}

			layer.style(rank_layer, {
				background: 'rgba(255,255,255,0)',
				border: 'none',
				height: '550px'
			});

			for(var i = 0; i < 3; i++) {
				$('.result-headimg')[i].src = $('.rank-headimg')[i].src;
				$('.result-name')[i].innerHTML = typeof($('.rank-name')[i].innerHTML) == undefined || $('.rank-name')[i].innerHTML == null ? 'Player' : $('.rank-name')[i].innerHTML;
				$('.result-point')[i].innerHTML = $('.rank-point')[i].innerHTML;
			}

		}, 'json').error(function(e) {
			layer.close(msg);
			layer.msg("出错了,请重试")
		})
	})

	//摇一摇重置游戏
	$('#game-rest').on('click', function(e) {

		if(gameInfo.gameIsRuning) {
			layer.msg("游戏进行中,请先关闭游戏")
		} else {
			gameInfo.shakeGame.init();
			$(this).hide();
			$("#game-start").show();

			var loading = layer.msg('结束中...', {
				icon: 16,
				shade: 0.01,
				time: 0
			});

			$.get(pageInfo.postUrl + "game/initGame", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {
				layer.close(loading);
				layer.msg('重置成功');
			}).error(function() {
				layer.close(loading);
				layer.msg('出错了 请重试');
			})

		}

	})

	//初始化抽奖
	$('#lottery-rest').on('click', function(e) {
		//询问框
		var v = layer.confirm('确定要重置抽奖吗？', {
			title: '提示',
			skin: 'layui-layer-lan',
			btn: ['确定', '取消'] //按钮
		}, function() {
			initLottery(true);
			layer.msg("成功重置")
		}, function() {

		});

		if(pageInfo.zoom < 1 || pageInfo.needResize) {
			layer.style(v, {
				top: '250px',
				left: '500px'
			})
		}

	})

	//	$('.damu-switch').on('click', function(e) {
	//		danmuModule.danmuSwitch();
	//	})

}

function getGameInfo(gameID) {
	gameInfo.currentGame = gameID;

	if(gameID == 'hdGame1') {
		playSpecialbgm(MUSIC_HUADENG);
	}

	//清除游戏等待人数
	clearGamenPeople();

	$('.module').removeClass('active');
	$('.module').removeClass('fadeIn');
	$('.module').hide();
	$('#youxi').addClass('this').siblings().removeClass('this');
	var id = $('#youxi').attr('data-des');
	//alert(id)
	//$('#gameErweima').attr('src',pageInfo.picHost +'/'+pageInfo.roomId +'/' +gameInfo.currentGame);
	//$('#gameWarp').attr('src',pageInfo.path+'/static/resource/img/game/'+gameID+'.png');
	$('#gameWarp').attr('src', 'http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/game/' + gameID + '.png');
	$('#' + id).addClass('active fadeIn');
	$('#' + id).show();
	$('#game-start').show();

	//	if(pageInfo.zoom<1||pageInfo.needResize){
	//		$('.leda').css('margin-top', '0px');
	//		$('#erweima').css('margin-top', '50px');
	//	}else{
	//		$('.leda').css('margin-top', (window.innerHeight - 140 - $('.leda').height()) / 2 + 'px');
	//		//$('#erweima').css('margin-top', (window.innerHeight - 140 - $('.leda').height()) / 2 + $('.game-wait-people').height() + 'px');
	//		$('#erweima').css('margin-top',(window.innerHeight - 140 - $('#erweima').height()) / 2);
	//	}

	// youxi

}

function connectToWebSocket() {
	if(window['WebSocket']) {
		myWebsocket = new ReconnectingWebSocket('ws:' + pageInfo.chatHost.replace(/http:/g, "") + 'marco?roomId=' + pageInfo.roomId + '&type=3');
		//myWebsocket = new ReconnectingWebSocket('ws://192.168.1.121:8087/haidong-chat/marco?roomId=' + pageInfo.roomId + '&type=3');
		myWebsocket.Debug = true;
		myWebsocket.timeoutInterval = 10000;
	} else
		myWebsocket = new SockJS('/haidong-h5/js/marco');

	myWebsocket.onopen = function() {
		//console.log('websocket open');
	};
	myWebsocket.onerror = function(e) {
		layer.msg('连接出错！' + e);
	}

	myWebsocket.onmessage = function(e) {
		console.log(e.data);
		var data = JSON.parse(e.data);
		if(data.code == 200 && data.type == 'vote') {
			if(gameInfo.currentGame == 'vote') {
				voteModule.resizeBar(data.data.voteResult, data.data.photoUrl, data.data.nickname, data.data.userId);
				//				if(data.data.voteResult == 'yes'){
				//					voteModule.resizeBar(true,data.data.photoUrl,data.data.nickname);
				//				}else{
				//					voteModule.resizeBar(false,data.data.photoUrl,data.data.nickname);
				//				}
			}
		} else if(data.code == 200 && data.type == 'chat' && danmuModule.setting.isOpen) {
			// 显示聊天内容
			danmuModule.postDanmu(data.data.photoUrl, data.data.userName, data.data.text);

			saveChatLocal({
				name: data.data.userName,
				type: 'chat',
				content: data.data.text
			});

		} else if(data.code == 200 && data.type == 'redpacket') {
			// hongbaoStatus.List.push(data.data);
			if(data.data.couple == 'isCouple') {
				//发给新人的红包
				danmuModule.postXrHb(data.data.photoUrl, data.data.userName, data.data.total, '给新人发了一个红包');
			} else {
				//发给全场的红包
				//danmuModule.postUFO(data.data.photoUrl, data.data.userName, data.data.text);
				danmuModule.postXrHb(data.data.photoUrl, data.data.userName, '???', '给全场发了一个红包');
			}

		} else if(data.code == 200 && data.type == 'login') {
			console.log('qiandao');
			if(data.data.state != 2) {
				if(data.data.type == "2") {
					//TODO   重复签到 暂时处理
					if(!checkIsSign(data.data.photoUrl)) {

						var reg = new RegExp("hdGame");
						if(reg.test(gameInfo.currentGame)) {
							//getPeople();
							needRefresh = true;
						}

						var n = parseInt($('#current-people').html()) + 1;
						localStorage.setItem("people", n);
						$('#current-people').html(n);

						//新签到用户存储
						userJoinedArr.push({
							'reheadimgurl': data.data.photoUrl,
							'renickname': data.data.userName,
							'wish': data.data.wish
						})
						hudongModule.user.push({
							'reheadimgurl': data.data.photoUrl,
							'renickname': data.data.userName,
							'wish': data.data.wish
						});
						hudongModule.luckyuser.push({
							'reheadimgurl': data.data.photoUrl,
							'renickname': data.data.userName,
							'wish': data.data.wish
						})

						put_hudong_people(data.data.photoUrl); //签到用户放入 互动游戏雷达区
						if(gameInfo.currentGame == 'sign') {
							signQ.queue.unshift({
								headimg: data.data.photoUrl,
								username: data.data.userName,
								wish: data.data.wish
							});
						} else {

						}

					}

				}
			}
		} else if(data.code == 200 && data.type == 'game') {

			if(data.data.type == 'people') {
				if(data.data.user_game_role) {
					putBhPeople(data.data.user_game_role, data.data.photoUrl, data.data.userId);
				} else {
					if(!gameManExist(data.data.userId)) {
						put_game_people(data.data.photoUrl);
					}
				}
			}
		} else if(data.type == 'gift') {
			// animet(data, data);
			//refresh gxb
			//getGXB();

			if(data.data.type == 2) {
				// 类型为2，代表祝福语礼物    新增弹幕17-04-21
				//giftWishQueue.push(data.data)

				if(data.data.giftId.indexOf('Domineering_LG1') != -1) {
					danmuModule.postBigD(data.data.photoUrl, data.data.userName, data.data.text, 20000, 1);
					saveChatLocal({
						name: data.data.userName,
						type: 'chat',
						content: data.data.text
					});
				} else if(data.data.giftId.indexOf('Domineering_LG2') != -1) {
					danmuModule.postBigD(data.data.photoUrl, data.data.userName, data.data.text, 25000, 1.2);
					saveChatLocal({
						name: data.data.userName,
						type: 'chat',
						content: data.data.text
					});
				} else if(data.data.giftId.indexOf('Domineering_LG3') != -1) {
					danmuModule.postBigD(data.data.photoUrl, data.data.userName, data.data.text, 30000, 1.5);
					saveChatLocal({
						name: data.data.userName,
						type: 'chat',
						content: data.data.text
					});
				} else {
					danmuModule.postDanmuWish(data.data.photoUrl, data.data.userName, data.data.text);
					saveChatLocal({
						name: data.data.userName,
						type: 'wish',
						content: data.data.text
					});
				}

			} else if(data.data.type == 1) {
				giftInfo.queue.unshift(data.data);
			}
		} else if(data.type == 'video') {
			$('#wishvideomenubtn').trigger('click');
			setTimeout('videoModule.startAni("' + data.data.videoUrl + '","' + data.data.address + '","' + data.data.userName + '")', 1000)

		} else if(data.type == 'lotteryGift') {

			if(!gameInfo.gameIsRuning) {
				hbNotice.queue.unshift({
					'username': data.data.userName,
					'money': data.data.money
				})
			}

		}
	};

	myWebsocket.onclose = function() {
		layer.msg('聊天服务器已经关闭');
		//console.log("服务关闭")
	};
}

connectToWebSocket();

/**
 * 初始化 签到页
 * @param {Object}
 *  flag 是否刷新签到页面的 头像
 */

function refreshSign(data) {
	$('.photo').empty();
	$('.photo').css({
		'background': '#cbcbcb',
		'opacity': '0.7'
	})
	$('#text-content').empty();

	var index = Math.floor(Math.random() * 46);

	for(var i = 0; i < data.length; i++) {
		if(i > 45) {
			break;
		}
		var img = document.createElement("img");
		// img.className = "rotate";
		img.src = data[i].reheadimgurl;
		photos[i].style.backgroundColor = "transparent";
		photos[i].style.opacity = 1;
		photos[i].appendChild(img);
	}
}

function startMobileGame() {
	gamePointInterval = setInterval(getGameRank, 2000);
}

function getGameRank() {
	$.get(pageInfo.postUrl + "game/scoreList", {
		gameCode: gameInfo.currentGame,
		liveId: pageInfo.roomId
	}, function(data) {
		// 获取游戏排名 更新排名
		var userList = data.data;
		restRank(userList);
		restRankLength(userList);
	}).error(function() {

	})
}

// 更新排名
function restRank(obj) {
	if(obj == null || typeof(obj) == undefined) {
		return
	}

	for(var i = 0; i < 6; i++) {
		console.log(obj.length);
		if(i >= obj.length)
			break;
		$('.rank-headimg')[i].src = obj[i].headimgurl;
		$('.rank-name')[i].innerHTML = cutString(6, obj[i].nickname);
		$('.rank-point')[i].innerHTML = obj[i].score + '分';
	}
	// 刷新进度条 分数去0
}

function restRankLength(obj) {
	if(obj == null || typeof(obj) == undefined) {
		return
	}

	if($('.rank-item')[0].style.width >= 1200) {
		// 排名第一已经到头
		if(obj.length >= 1) {
			for(var j = 1; j < obj.length; j++) {
				$('.rank-item')[j].style.width = obj[j].score * 840 / obj[0].score + 'px';
			}
		}
	} else {
		for(var i = 0; i < obj.length; i++) {
			if(obj[0].score < 100) {
				$('.rank-item')[i].style.width = obj[i].score * 10 + 360 + 'px';
			} else if(obj[0].score > 100) {
				if(obj[i].score / 10 > 840) {
					$('.rank-item')[i].style.width = '1200px';
				} else {
					$('.rank-item')[i].style.width = obj[i].score / 10 + 360 + 'px';
				}
			}
		}
	}
}

function cutString(strlength, content) {
	if(strlength == null || content == null) {
		return '';
	}
	if(content.length > strlength) {
		return content.substr(0, strlength) + '..';
	} else {
		return content;
	}
}

/**
 * 获取大屏
 * @param {Object} flag  是否刷新签到页面的 头像
 */
function getPeople(flag) {
	hudongModule.user.length = 0;
	hudongModule.luckyuser.length = 0;
	userJoinedArr.length = 0;
	$.get(gameHost + "/reportController/report/" + pageInfo.roomId, function(data) {
		$('#current-people').html(data.data.length + '');
		$('#hudong-wait-people-num').html(data.data.length + '');

		if(flag) {
			//refreshSign(data.data);
			userJoinedArr.length = 0;
			userJoinedArr = data.data;
			//首次加载 
			console.log(userJoinedArr);
			$('#hudong-leida .game-head').remove();
			for(var i = 0; i < data.data.length; i++) {
				put_hudong_people(data.data[i].reheadimgurl);
			}
			$('#hudong-leida .game-head').attr('onerror', 'errorImg(this)');
			hudongModule.user.length = 0;
			hudongModule.luckyuser.length = 0;
			hudongModule.user = simpleCopy(data.data);
			hudongModule.luckyuser = simpleCopy(data.data);
			//签到人数 存储到本地
			localStorage.setItem('people', data.data.length + '')
		}

	}, 'json').error(function() {
		layer.msg('get people error')
	})
}

/**
 * 初始化 签到页
 * @param {Object} flag  是否刷新签到页面的 头像
 */

function refreshSign(data) {
	$('.photo').empty();
	$('.photo').css({
		'background': '#cbcbcb',
		'opacity': '0.7'
	})
	$('#text-content').empty();

	var index = Math.floor(Math.random() * 46);

	for(var i = 0; i < data.length; i++) {
		if(i > 45)
			break;
		var img = document.createElement("img");
		//		img.className = "rotate";
		img.src = data[i].reheadimgurl;
		photos[index].style.backgroundColor = "transparent";
		photos[index].style.opacity = 1;
		photos[index].appendChild(img);
	}
}

/**
 * 初始化大屏当前项目
 * @param {Object} flag  是否刷新签到页面的 头像
 */
function initModule(successCallback, errorCallback) {
	$.post("demo_ajax_gethint.asp", {
		suggest: txt
	}, function(result) {
		$("span").html(result);
	}).error(function() {});
}

/**
 * 游戏等待雷达界面  添加用户头像
 * @param {Object} headimg  用户头像
 */
function put_game_people(headimg) {
	var img = document.createElement("img");
	img.src = pageInfo.picHost + headimg;
	img.className = 'game-head';
	img.style.left = danmuModule.getRandom(400, 0) + 'px';
	img.style.top = danmuModule.getRandom(400, 0) + 'px';
	//img.style.zoom = pageInfo.zoom;
	img.style.zoom = 1.5;
	document.getElementById("game-leida").appendChild(img);
	$('#game-wait-people-num').html(gameInfo.manList.length);
}

/**
 * 互动游戏  添加用户头像
 * @param {Object} headimg  用户头像
 */
function put_hudong_people(headimg) {
	var img = document.createElement("img");
	img.src = pageInfo.picHost + headimg;
	//img.on
	img.className = 'game-head';
	img.style.left = danmuModule.getRandom(400, 0) + 'px';
	img.style.top = danmuModule.getRandom(400, 0) + 'px';
	img.onerror = 'errorImg(this)'
	//	img.style.zoom = pageInfo.zoom;
	document.getElementById("hudong-leida").appendChild(img);
	$('#game-wait-people-num').html(gameInfo.manList.length);

}

/**
 * 清除游戏等待人数
 * @param {Object} 
 */
function clear_game_head() {
	gameInfo.manList.length = 0;
	$('.game-head').remove();
	$('#game-wait-people-num').html(gameInfo.manList.length);
}

/**
 * 游戏等待时 该用户是否已经存在
 * @param {Object} userid   用户ID
 */
function gameManExist(userid) {
	for(var i = 0; i < gameInfo.manList.length; i++) {
		if(userid == gameInfo.manList[i]) {
			return true;
		}
	}
	gameInfo.manList.push(userid);
	return false;
}

/**
 * 清除游戏等待人数
 * @param {Object} 
 */
function clearGamenPeople() {
	gameInfo.manList.length = 0;
	$('#game-leida .game-head').remove();
	$('#game-wait-people-num').html(gameInfo.manList.length);
}

//游戏开始前倒计时 结束事件
document.getElementById("game-ready").addEventListener('webkitAnimationEnd', function() {
	//游戏开始
	$('.tip-box-start').hide();

	//alert('1')
	startShakeGame();

	$('#man-run').addClass('husband-run-x');
	//倒数计时开始
	$('.num').addClass('count-down');
	$('.tip-box').show();
	gameInfo.shakeGame.runInterval = setInterval("toggleRun()", 5000);

})

/**
 * 开始摇一摇游戏
 * @param {Object} 
 */
function startShakeGame() {
	var loading = layer.msg('加载中', {
		icon: 16,
		shade: 0.01,
		time: 0
	});

	$.get(pageInfo.postUrl + "game/beginGame", {
		gameCode: gameInfo.currentGame,
		liveId: pageInfo.roomId
	}, function(result) {
		layer.close(loading);
		console.log("youxi kaishi ");
		$('.tip-shake').show();
		gameInfo.shakeGame.getpointInterval = setInterval(getshakepoint, 2000)
	}).error(function() {
		layer.close(loading);
	})
}

/**
 * 获取摇一摇分数
 * @param {Object} 
 */
function getshakepoint() {
	//TODO 
	$.get(pageInfo.postUrl + "game/shakeResult", {
		gameCode: gameInfo.currentGame,
		roomId: pageInfo.roomId
	}, function(result) {
		if(result.code == 200) {
			restShakeRank(result.data);
		}
	}).error(function() {})
}

/**
 * 更新摇一摇排行榜
 * @param {Object} 
 */
function restShakeRank(obj) {
	for(var i = 0; i < obj.length; i++) {
		$('#shake-run-rank .username')[i].innerHTML = cutName(obj[i].nickname);
		$('#shake-run-rank .userpoint')[i].innerHTML = Math.round(obj[i].score);
		$('#shake-run-rank .user')[i].src = obj[i].headimgurl;
		//console.log(obj[i].score);
		$('#shake-run-rank .rank-item-b')[i].dataset.point = Math.round(obj[i].score);
		//更新进度条
		$('#shake-run-rank .progress-bar')[i].style.height = ((obj[i].score / gameInfo.shakeGame.progressTotal) * 150 + 10) + 'px';
	}

}

/**
 * 摇一摇游戏结束
 * @param {Object} 
 */
document.getElementById("man-run").addEventListener('webkitAnimationEnd', function() {
	clearInterval(gameInfo.shakeGame.runInterval);
	clearInterval(gameInfo.shakeGame.getpointInterval);

	$('.husband').hide();
	$('.wife').hide();

	$('.kiss').show();
	$('.kiss').addClass('kiss-ani');

	//背景动画移除
	$('.front').removeClass('move1');
	$('.back').removeClass('move2');
	$('.boat1').removeClass('move3');

	$('.husband').hide();
	$('.wife').hide();

	$('.kiss').show();
	$('.kiss').addClass('kiss-ani');

	//背景动画移除
	$('.front').removeClass('move1');
	$('.back').removeClass('move2');
	$('.boat1').removeClass('move3');

	$.get(pageInfo.postUrl + 'game/endGame', {
		liveId: pageInfo.roomId,
		gameCode: gameInfo.currentGame
	}, function(data) {
		//		alert('end');
		rank_layer = layer.open({
			type: 1,
			closeBtn: '0',
			title: '',
			skin: 'layui-layer-rim', //加上边框
			area: ['40%', '70%'], //宽高
			content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_09.png"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
			//			move: '.layui-layer-content',
			shadeClose: 'true',
			resize: 'false',
		});

		//		if(pageInfo.zoom<1||pageInfo.needResize){
		//			layer.style(rank_layer, {
		//				background: 'rgba(255,255,255,0)',
		//				border: 'none',
		//				top:'100px',
		//				left:'400px',
		//				scrollbar:false,
		//				height:'550px'
		//			});
		//		}else{
		//			layer.style(rank_layer, {
		//				background: 'rgba(255,255,255,0)',
		//				border: 'none',
		//				height:'550px'
		//			});
		//		}

		layer.style(rank_layer, {
			background: 'rgba(255,255,255,0)',
			border: 'none',
			height: '550px'
		});

		for(var i = 0; i < 3; i++) {
			//console.log($('.touxiang')[i].children[0].src);
			$('.result-headimg')[i].src = $('#shake-run-rank .touxiang')[i].children[1].src;
			$('.result-name')[i].innerHTML = typeof($('#shake-run-rank .username')[i].innerHTML) == undefined || $('#shake-run-rank .username')[i].innerHTML == null ? 'Player' : $('#shake-run-rank .username')[i].innerHTML;
			$('.result-point')[i].innerHTML = $('#shake-run-rank .userpoint')[i].innerHTML;
		}
		gameInfo.gameIsRuning = false;

	}, 'json').error(function(e) {
		layer.close(msg);
		layer.msg("出错了,请重试")
	})
	gameInfo.gameIsRuning = false;

	$('.tip-shake').hide();

})

//切换跑步姿态
function toggleRun() {
	if($('.husband').hasClass('m')) {
		$('.husband').css('background-image', 'url(' + pageInfo.path + '/static/resource/img/game/runfast.png)');
		$('.husband').addClass('n');
		$('.husband').removeClass('m');

	} else if($('.husband').hasClass('n')) {
		$('.husband').css('background-image', 'url(' + pageInfo.path + '/static/resource/img/game/run.png)');
		$('.husband').addClass('m');
		$('.husband').removeClass('n');
	}
}

observe(giftInfo, function(name, value, old) {
	//console.log(name + "__" + value + "__" + old);
	//console.log(JSON.stringify(value))
	if(name == 'Array-unshift') {
		if(!giftInfo.status && giftInfo.queue.length > 0) {
			showGift();
		}
	}
})

var giftGIFimg = document.querySelector("#gift img");

var _giftAniType = [];
var _giftSettings = {
	//huajia
	"love_HJ": "ship",
	"flower_HJ": "flower",
	"child_HJ": "child",
	"dance_HJ": "dance",
	"kiss_HJ": "kiss",
	"cupid_HJ": "heart",
	"romantic_HJ": "huajia",
	"forever_HJ": "forever",
	"plane_HJ": "plane",
	"ferrari_HJ": "ferrari",
	"c919_HJ": "c919",

	//base
	"love": "ship",
	"flower": "flower",
	"child": "child",
	"dance": "dance",
	"kiss": "kiss",
	"cupid": "heart",
	"forever": "forever",
	"plane": "plane",
	"ferrari": "ferrari",
	"c919": "c919",
}

function showGift() {
	var gift;
	if(giftInfo.queue.length > 0) {
		giftInfo.status = true;
		gift = giftInfo.queue.pop();
	} else {
		giftInfo.status = false;
		return;
	}

	//				var index = Math.floor(Math.random() * 3);
	var time = 0;
	//				document.getElementById("giftcome").play();
	//console.log(gift);
	//	liveOBJ.playVoice();

	//	if(_giftcome_voice.paused) {
	//		console.log('play vioice');
	//		_giftcome_voice.play();
	//	}

	console.log('show gift');
	var img = document.createElement("img")
	switch(_giftSettings[gift.giftId]) {
		case "ship":
			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +
				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了' +
				'<span class="color-text">[爱情游艇]大礼包' + '</span></div><div class="UserText">' +
				'祝新人恩爱甜蜜，幸福长久!</div></div></div>'
			showGiftShip();

			time = 8200;
			break;

		case "dance":
			//document.getElementById("gift").innerHTML = '<img style="height:100%;width:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[3] + '" />';

			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[3];

			giftGIFimg.style.height = "100%";
			giftGIFimg.style.width = "auto";

			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[跳个舞]大礼包' + '</span></div><div class="UserText">' +
				'对新人说:要不要来一段啊!</div></div></div>'

			time = 5000;
			break;
		case "kiss":
			//document.getElementById("gift").innerHTML = '<img style="height:100%;width:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[1] + '" />';

			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[1];

			giftGIFimg.style.height = "100%";
			giftGIFimg.style.width = "auto";

			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[亲一个]大礼包' + '</span></div><div class="UserText">' +
				'对新人说:亲一个，亲一个!</div></div></div>'
			time = 5000;
			break;
		case "flower": //花
			//document.getElementById("gift").innerHTML = '<img style="width:100%;height:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[2] + '" />';

			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[2];

			giftGIFimg.style.height = "auto";
			giftGIFimg.style.width = "100%";

			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[玫瑰花开]大礼包' + '</span></div><div class="UserText">' +
				'祝新人新婚快乐，甜甜蜜蜜!</div></div></div>'
			time = 3500;
			break;
		case "heart": //
			//document.getElementById("gift").innerHTML = '<img style="width:100%;height:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[0] + '" />';

			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[0];

			giftGIFimg.style.height = "auto";
			giftGIFimg.style.width = "100%";

			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[百年好合]大礼包' + '</span></div><div class="UserText">' +
				'祝新人百年好合，永结同心!</div></div></div>'
			time = 5500;
			break;

		case "child": //
			//document.getElementById("gift").innerHTML = '<img style="height:100%;width:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[4] + '" />';

			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[4];

			giftGIFimg.style.height = "100%";
			giftGIFimg.style.width = "auto";

			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[早生贵子]大礼包' + '</span></div><div class="UserText">' +
				'祝新人早生贵子，百年好合！</div></div></div>'
			time = 5000;
			break;

		case "huajia":
			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[新婚快乐]大礼包' + '</span></div><div class="UserText">' +
				'祝新人永结同心，百年好合！</div></div></div>'
			huajiaGift.start();
			time = 7500;
			break;
		case "forever":
			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[三生三世]大礼包' + '</span></div><div class="UserText">' +
				'祝新人永结同心，百年好合！</div></div></div>'
			sanshengGift.start();
			time = 7500;
			break;
		case "plane":
			hdAPP.gift.plane.start();
			danmuModule.sendDanmuByNum(5, gift.photoUrl, gift.userName + "送来了[发财飞机]", '祝愿全场心想事成，财源广进！');
			time = 10000;
			break;
		case "ferrari":
			hdAPP.gift.ferrari.start();
			danmuModule.sendDanmuByNum(5, gift.photoUrl, gift.userName + "送来了[豪华跑车]", '条条道路风帆顺,四季平安载誉来！');
			time = 10000;
			break;
		case "c919":
			hdAPP.gift.c919.start();
			danmuModule.sendDanmuByNum(5, gift.photoUrl, gift.userName + "送来了[中国梦]", '祝愿全场心想事成，飞黄腾达！');
			time = 10000;
			break;
		default:
			break;
	}

	setTimeout(function() {
		giftGIFimg.src = "";
		showGift();
	}, time)
}

//是否已经签到
function checkIsSign(parma) {
	for(var i = 0; i < userJoinedArr.length; i++) {
		if(parma === userJoinedArr[i].reheadimgurl) {
			return true;
		}
	}
	return false;
}

function cutName(name) {
	if(name.length > 5) {
		return name.substr(0, 5) + ".."
	} else {
		return name;
	}
}

//var simpleCopy = function(o){
function simpleCopy(o) {
	if(o instanceof Array) {
		var n = [];
		for(var i = 0; i < o.length; ++i) {
			n[i] = o[i];
		}
		return n;
	} else if(o instanceof Object) {
		var n = {}
		for(var i in o) {
			n[i] = o[i];
		}
		return n;
	}
}

//play ship
function showGiftShip() {
	if($('#shipGift')[0].style.display == 'none')
		$('#shipGift').show();
	if($('#shipGift').hasClass('fadeIn'))
		return;
	$('#shipGift').addClass('fadeIn');
	$('#shipGift .gift-dp').addClass('dp-move1');
	$('#shipGift .gitf-ship-s').addClass('gitf-ship-move');

	setTimeout(function() {
		$('#shipGift').addClass('fadeOut');
		//$('#shipGift').removeClass('fadeIn');
		$('#shipGift .gift-dp').removeClass('dp-move1');
		$('#shipGift .gitf-ship-s').removeClass('gitf-ship-move');
		setTimeout(function() {
			$('#shipGift').removeClass('fadeIn');
			$('#shipGift').removeClass('fadeOut');
		}, 1000)
	}, 7000)
}

function requestFullScreen(element) {
	// 判断各种浏览器，找到正确的方法
	var requestMethod = element.requestFullScreen || //W3C
		element.webkitRequestFullScreen || //Chrome等
		element.mozRequestFullScreen || //FireFox
		element.msRequestFullScreen; //IE11
	if(requestMethod) {
		requestMethod.call(element);
	} else if(typeof window.ActiveXObject !== "undefined") { //for Internet Explorer
		var wscript = new ActiveXObject("WScript.Shell");
		if(wscript !== null) {
			wscript.SendKeys("{F11}");
		}
	}
	// resizeHtml();
}

//退出全屏 判断浏览器种类
function exitFull() {
	// 判断各种浏览器，找到正确的方法
	var exitMethod = document.exitFullscreen || //W3C
		document.mozCancelFullScreen || //Chrome等
		document.webkitExitFullscreen || //FireFox
		document.webkitExitFullscreen; //IE11
	if(exitMethod) {
		exitMethod.call(document);
	} else if(typeof window.ActiveXObject !== "undefined") { //for Internet Explorer
		var wscript = new ActiveXObject("WScript.Shell");
		if(wscript !== null) {
			wscript.SendKeys("{F11}");
		}
	}
}

function fullSc() {
	//console.log(obj)
	if(this.classList.contains('done')) {
		exitFull();
		this.classList.remove('done')
	} else {
		requestFullScreen(document.documentElement);
		this.classList.add('done')
	}
}

/* 开关礼物榜区
 */
function toogleCW() {

	if($('#cw')[0].style.visibility == 'hidden') {
		$('#cw')[0].style.visibility = 'visible';
		$('.cw-chat-list').show();

	} else {
		$('#cw')[0].style.visibility = 'hidden';
		$('.cw-chat-list').hide();
	}

	initCwChat();
}

function showCW() {
	$('#cw')[0].style.visibility = 'visible';
	$('.cw-chat-list').show();
	initCwChat();

};

function hideCW() {
	$('#cw')[0].style.visibility = 'hidden';
	$('.cw-chat-list').hide();
};

function getGXB() {
	$.get(pageInfo.postUrl + "game/list", {
		liveid: pageInfo.roomId
	}, function(result) {
		if(result.code == 200) {
			if(result.data.length > 0) {
				for(var i = 0; i < result.data.length; i++) {
					$('.cw-money-list ul li')[i].children[0].src = result.data[i].headimgurl;
					$('.cw-money-list ul li')[i].children[1].innerHTML = result.data[i].nickname;
					$('.cw-money-list ul li')[i].children[2].innerHTML = parseInt(result.data[i].total);
				}
			}
		}
	})
}

/*
 * save chat
 */
function saveChatLocal(obj) {
	if(!obj)
		return;
	var c = JSON.parse(localStorage.getItem(pageInfo.roomId));
	if(!c) {
		c = {};
		c.data = []
	}

	c.data.push(obj);

	localStorage.setItem(pageInfo.roomId, JSON.stringify(c))

	if(obj.type == 'chat') {
		$('#scrollDiv').append('<li class="cw-chat-item" onclick="chatBlock(' + obj.id + ')">' + obj.name + ':<span class="cw-chat-normal">' + obj.content + '</span></li>')
	} else if(obj.type == 'wish') {
		//$('#scrollDiv').append('<li class="cw-chat-item cw-chat-wish">'+obj.name+':<span class="cw-chat-normal">'+obj.content+'</span></li>')
	} else if(obj.type == 'gift') {
		//$('#scrollDiv').append('<li class="cw-chat-item cw-chat-gift"><span>'+obj.name+'&nbsp;</span><span class="cw-chat-normal">送来了：</span> <img src="'+_getGiftImg(obj.giftid)+'" width="150px"></li>')
	}
	initCwChat();
}

function chatBlock(id) {
	//	layer.confirm('是否屏蔽该用户', {icon: 3, title:'提示'}, function(index){
	//		  //do something
	//		  console.log(index);
	//		  layer.close(index);
	//		});
}

/*
 * get local chat
 */
function getChatLocal() {
	var c = JSON.parse(localStorage.getItem(pageInfo.roomId));
	if(!c || c.data.length == 0)
		return;
	for(var i = 0; i < c.data.length; i++) {
		if(c.data[i].type == 'chat') {
			$('#scrollDiv').append('<li class="cw-chat-item" onclick="chatBlock(' + c.data[i].id + ')">' + c.data[i].name + ':<span class="cw-chat-normal">' + c.data[i].content + '</span></li>')
		} else if(c.data[i].type == 'wish') {
			$('#scrollDiv').append('<li class="cw-chat-item cw-chat-wish" onclick="chatBlock(' + c.data[i].id + ')">' + c.data[i].name + ':<span class="cw-chat-normal">' + c.data[i].content + '</span></li>')
		} else if(c.data[i].type == 'gift') {
			$('#scrollDiv').append('<li class="cw-chat-item cw-chat-gift" onclick="chatBlock(' + c.data[i].id + ')"><span>' + c.data[i].name + '&nbsp;</span><span class="cw-chat-normal">送来了：</span> <img src="' + _getGiftImg(c.data[i].giftid) + '" width="150px"></li>')
		}
	}

	initCwChat();
}

function _getGiftImg(giftid) {
	return pageInfo.path + 'static/resource/img/giftsmall/' + giftid + '.png';
}

var cwInterval;
var cwIndex = 0;
var cwPx = 0;

//聊天滚动
function initCwChat() {
	if(cwInterval)
		return;

	if(document.getElementById("scrollDiv").offsetHeight > document.getElementById("scrollDiv").parentElement.offsetHeight) {
		//copy
		$('#scrollDiv-copy').html($('#scrollDiv').html());
		//alert('开始滚动了');
		cwInterval = setInterval(scrllCw, 2000);
	}
}

function scrllCw() {
	//当前显示第一个li高度
	if(cwIndex < document.getElementById("scrollDiv").children.length) {
		document.getElementById("scrollDiv").style.transition = 'ease 0.2s';
		document.getElementById("scrollDiv-copy").style.transition = 'ease 0.2s';
		document.getElementById("scrollDiv").style.webkitTransform = 'translateY(-' + (cwPx + document.getElementById("scrollDiv").children[cwIndex].offsetHeight + 10) + 'px)';
		document.getElementById("scrollDiv-copy").style.webkitTransform = 'translateY(-' + (cwPx + document.getElementById("scrollDiv").children[cwIndex].offsetHeight + 10) + 'px)';
		cwPx += document.getElementById("scrollDiv").children[cwIndex].offsetHeight + 10;
		cwIndex++;
	} else {
		document.getElementById("scrollDiv").style.transition = 'ease 0s';
		document.getElementById("scrollDiv-copy").style.transition = 'ease 0s';
		document.getElementById("scrollDiv-copy").style.webkitTransform = 'translateY(0)';
		document.getElementById("scrollDiv").style.webkitTransform = 'translateY(0)';
		cwIndex = 0;
		cwPx = 0;
	}
}

function initBahe() {
	$('.w-people-warp-r div').empty();
	$('.w-people-warp-l div').empty();
	$('.t-red-people').html('0人');
	$('.t-blue-people').html('0人');

	$('.t-red-point').html('0分');
	$('.t-blue-point').html('0分');

	$('#game-start').show();
}

/**
 * 拔河游戏 初始化
 */
function initGameBh() {
	$('#game-start').show();
	gameInfo.blueTeam.length = 0;
	gameInfo.redTeam.length = 0;
	$('.t-red-people').html('0人');
	$('.t-blue-people').html('0人');

	$('.w-people-warp-l div').empty();
	$('.w-people-warp-r div').empty();

	$('#bh-countdown').hide();
	$('#bh-countdown div').removeClass('count-down');

	$('#bhgame-ready').removeClass('game-ready');
	$('#bhgame-ready').show();

	$('.t-red-point').html('0分');
	$('.t-blue-point').html('0分');

	$('.bahe-people-lr').show();
	$('.bahe-result').hide();

	$('.bahe-people-lr').removeClass('ani-bahe-people');
	$('.bahe-people').removeClass('bahe-people-move');
	$('.rope').show();
	$('#bhgame-ready').removeClass('game-ready');
	$('#bh-countdown div').removeClass('count-down');

	$('.bahe-head-l li').empty();
	$('.bahe-head-r li').empty();

	$('.bahe-people-lr').css('left', '0');
}

/**
 * 游戏等待时 该用户是否已经存在  
 * @param {Object} userid   用户ID
 * @param {Object} list   用户集合
 */
function gameManExistT(userid, list) {
	for(var i = 0; i < list.length; i++) {
		if(userid == list[i]) {
			return true;
		}
	}
	list.push(userid);
	return false;
}

/**
 * 拔河游戏 玩家加入
 * @param {Object} 
 */
function putBhPeople(color, img, userid) {
	if(color == 'red') {
		if(!gameManExistT(userid, gameInfo.redTeam)) {
			$('.w-people-warp-l div').append('<img src="' + img + '"/>');
			if($('.w-people-warp-l div')[0].offsetHeight > $('.w-people-warp-l')[0].offsetHeight) {
				$('.w-people-warp-l div')[0].style.transition = 'ease 0.2s';
				$('.w-people-warp-l div')[0].style.webkitTransform = 'translateY(-' + ($('.w-people-warp-l div')[0].offsetHeight - $('.w-people-warp-l')[0].offsetHeight) + 'px)';
			}
			$('.t-red-people').html(gameInfo.redTeam.length + '人');
		}
	} else if(color == 'blue') {
		if(!gameManExistT(userid, gameInfo.blueTeam)) {
			$('.w-people-warp-r div').append('<img src="' + img + '"/>')
			if($('.w-people-warp-r div')[0].offsetHeight > $('.w-people-warp-r')[0].offsetHeight) {
				$('.w-people-warp-r div')[0].style.transition = 'ease 0.2s';
				$('.w-people-warp-r div')[0].style.webkitTransform = 'translateY(-' + ($('.w-people-warp-r div')[0].offsetHeight - $('.w-people-warp-r')[0].offsetHeight) + 'px)';
			}
			$('.t-blue-people').html(gameInfo.blueTeam.length + '人');
		}
	}
}

var bhGameInterval;

/**
 * 获取拔河分数
 * @param {Object} 
 */
function getBahepoint() {
	//TODO 
	$.get(pageInfo.postUrl + "/game/tugOfWarResult", {
		gameCode: gameInfo.currentGame,
		roomId: pageInfo.roomId
	}, function(result) {
		//console.log(result);
		if(result) {
			restGameBh(result.average_red, result.average_blue, result.red_result, result.blue_result)
		}
	}, 'json').error(function() {})
}

/**
 * 拔河游戏 开始
 */
function startBaheGame() {
	$('#btn-bahe').removeClass('this');
	$('#bhgame-ready').show();
	$('#bhgame-ready').addClass('game-ready');

	setTimeout(function() {
		$('.bahe-people-lr').addClass('ani-bahe-people');
		$('.bahe-people').addClass('bahe-people-move');
		$('#bhgame-ready').hide();
		$('#bh-countdown').show();
		$('#bh-countdown div').addClass('count-down');

		bhGameInterval = setInterval(getBahepoint, 2000);
		//		$('.tip-shake1').show();
		setTimeout(function() {
			endBaheGame();
		}, 30000)
	}, 5000)
}

/**
 * 拔河游戏 结束
 */
function endBaheGame() {
	$('.bahe-people-lr').hide();
	$('.rope').hide();
	gameInfo.gameIsRuning = false;
	clearInterval(bhGameInterval);

	var resultkuang;
	var rwin;

	if(parseFloat($('.t-red-point').attr('data-point')) > parseFloat($('.t-blue-point').attr('data-point'))) {
		$('.bahe-result img').attr('src', pageInfo.path + '/static/resource/img/bahe/rwin.png')
		resultkuang = pageInfo.path + '/static/resource/img/bahe/rwinkuang.png';
		rwin = true;
	} else {
		$('.bahe-result img').attr('src', pageInfo.path + '/static/resource/img/bahe/rwin.png')
		resultkuang = pageInfo.path + '/static/resource/img/bahe/bwinkuang.png';
		rwin = false;
	}

	rank_layer = layer.open({
		type: 1,
		closeBtn: '0',
		title: '',
		skin: 'layui-layer-rim', // 加上边框
		area: ['40%', '70%'], // 宽高
		content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + resultkuang + '"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
		// move: '.layui-layer-content',
		shadeClose: 'true',
		resize: 'false',
	});

	//	if(pageInfo.zoom<1||pageInfo.needResize){
	//		layer.style(rank_layer, {
	//			background: 'rgba(255,255,255,0)',
	//			border: 'none',
	//			top:'100px',
	//			left:'400px',
	//			scrollbar:false,
	//			height:'550px'
	//		});
	//	}else{
	//		layer.style(rank_layer, {
	//			background: 'rgba(255,255,255,0)',
	//			border: 'none',
	//			height:'550px'
	//		});
	//	}

	layer.style(rank_layer, {
		background: 'rgba(255,255,255,0)',
		border: 'none',
		height: '550px'
	});

	if(rwin) {
		$('.bahe-result img').attr('src', pageInfo.path + '/static/resource/img/bahe/rwin.png')
		for(var i = 0; i < 3; i++) {
			if(!$('.bahe-head-l li')[i].children[1]) {
				break;
			} //console.log($('.touxiang')[i].children[0].src);
			$('.result-headimg')[i].src = $('.bahe-head-l li')[i].children[1].src;
			$('.result-name')[i].innerHTML = typeof($('.bahe-head-l li')[i].children[1].getAttribute('data-name')) == undefined || $('.bahe-head-l li')[i].children[1].getAttribute('data-name') == null ? 'Player' : cutName($('.bahe-head-l li')[i].children[1].getAttribute('data-name'));
			$('.result-point')[i].innerHTML = parseInt($('.bahe-head-l li')[i].children[1].getAttribute('data-point')) + '分';
		}

	} else {
		$('.bahe-result img').attr('src', pageInfo.path + '/static/resource/img/bahe/bwin.png')
		for(var i = 0; i < 3; i++) {
			//console.log($('.touxiang')[i].children[0].src);
			if(!$('.bahe-head-r li')[i].children[1]) {
				break;
			}
			$('.result-headimg')[i].src = $('.bahe-head-r li')[i].children[1].src;
			$('.result-name')[i].innerHTML = typeof($('.bahe-head-r li')[i].children[1].getAttribute('data-name')) == undefined || $('.bahe-head-r li')[i].children[1].getAttribute('data-name') == null ? 'Player' : cutName($('.bahe-head-r li')[i].children[1].getAttribute('data-name'));
			$('.result-point')[i].innerHTML = parseInt($('.bahe-head-r li')[i].children[1].getAttribute('data-point')) + '分';
		}

	}

	$('.bahe-result').show();

	$.get(pageInfo.postUrl + 'game/endGame', {
		liveId: pageInfo.roomId,
		gameCode: gameInfo.currentGame
	}, function(data) {
		//拔河游戏结束
	})
	//	$('.tip-shake1').hide();
}
/**
 * 拔河游戏 刷新分数
 * @param {Object} 
 */
function restGameBh(redscroe, bluescroe, redHead, blueHead) {
	var c = redscroe - bluescroe;

	$('.t-red-point').html(Math.round(redscroe) + '分');
	$('.t-blue-point').html(Math.round(bluescroe) + '分');
	$('.t-red-point').attr('data-point', redscroe);
	$('.t-blue-point').attr('data-point', bluescroe);

	$('.bahe-head-l li').empty();
	$('.bahe-head-r li').empty();

	for(var i = 0; i < redHead.length; i++) {
		$('.bahe-head-l li')[i].innerHTML = '<span>' + redHead[i].nickname + '</span><img data-point="' + redHead[i].score + '" data-name="' + redHead[i].nickname + '" src="' + redHead[i].headimgurl + '" />';
	}

	for(var j = 0; j < blueHead.length; j++) {
		$('.bahe-head-r li')[j].innerHTML = '<span>' + blueHead[i].nickname + '</span><img data-point="' + blueHead[j].score + '" data-name="' + blueHead[j].nickname + '" src="' + blueHead[j].headimgurl + '" />';
	}

	if(Math.abs(c) < 100) {
		$('.bahe-people-lr').css('left', -c + 'px');
	} else {
		if(c > 0) {
			$('.bahe-people-lr').css('left', '-100px');
		} else {
			$('.bahe-people-lr').css('left', '100px');
		}
	}
}

document.getElementById("dp-move").addEventListener('webkitAnimationEnd', function() {
	//	alert('111');
	this.classList.remove('dp-move');
	setTimeout(function() {
		//		this.classList.remove('dp-move');
		document.getElementById("dp-move").classList.add('dp-move');
	}, 3000)
})

var gameCountTime = null;
var vargameCountTimeOut = null;
var countDownflag = false;

//小游戏倒计时  3MIN
function toogleCountDown() {
	if(countDownflag) {
		countDownflag = false;
		$('#mgamecountdown').hide();
		$('.time-part.minutes')[0].classList.remove('tens');
		$('.time-part.minutes')[1].classList.remove('ones');

		$('.time-part.seconds')[0].classList.remove('tens');
		$('.time-part.seconds')[1].classList.remove('ones');

		$('.time-part.hundredths')[0].classList.remove('tens');
		$('.time-part.hundredths')[1].classList.remove('ones');
		clearTimeout(gameCountTimeOut);
		clearTimeout(gameCountTime);
	} else {
		$('#mgamecountdown').show();
		countDownflag = true;
		$('.time-part.minutes')[1].children[0].style.webkitTransform = 'translateY(-1440px)';
		//延迟20S 后开始倒计时		
		gameCountTimeOut = setTimeout(function() {
			$('.time-part.minutes')[1].children[0].style.webkitTransform = 'translateY(-0px)';
			$('.time-part.minutes')[0].classList.add('tens');
			$('.time-part.minutes')[1].classList.add('ones');
			$('.time-part.seconds')[0].classList.add('tens');
			$('.time-part.seconds')[1].classList.add('ones');
			$('.time-part.hundredths')[0].classList.add('tens');
			$('.time-part.hundredths')[1].classList.add('ones');
			//2分钟后游戏结束
			gameCountTime = setTimeout(function() {
				$('#game-end').click();
			}, 119000)
		}, 5000)
	}
}

//2017-11-23
(function(a) {
	console.log(a);
	var time = 30;
	var gInterval = null;
	var rankInterval = null;

	//重置
	a.init = function() {
		time = 30;
		$('.money-stage .ds').html(time);
		$('.money-stage .sky').removeClass('move');
		$('.money-stage .ship').removeClass('move');
		$('.money-stage .ground').removeClass('move');
		$('.money-stage .man').removeClass('move');
		$('.money-stage .man .p img').removeClass('move');
		$('.money-stage .man .l img').removeClass('move');
		$('.money-stage .man .s img').hide();

		$('.money-stage .goldbox').removeClass('move');
		$('.money-stage .facai').removeClass('move');

		$($('.money-stage .man .p')[0]).hide();
		$($('.money-stage .man .p')[1]).show();
		$('.money-stage .ds').hide();

		$('#money-rank .progress-bar').css('height', '10px');
		$('#money-rank .user').attr('src', pageInfo.path + '/static/resource/img/game/abc_03.png');
		$('#money-rank .username').html('Payer');
		$('#money-rank .userpoint').html('0');

	};
	//开始
	a.start = function() {
		playSpecialbgm(MUSIC_MONEY_2);
		gameInfo.gameIsRuning = true;

		$('.money-stage .tip-box-start').show();
		$('.money-stage .tip-box-start').addClass('game-ready');
		setTimeout(function() {
			$('.money-stage .tip-box-start').hide();
			$('.money-stage .tip-box-start').removeClass('game-ready');
			$('.money-stage .ds').show();
			$('.money-stage .ship').addClass('move');
			$('.money-stage .sky').addClass('move');
			$('.money-stage .ground').addClass('move');
			$('.money-stage .man').addClass('move');
			$('.money-stage .man .p img').addClass('move');
			$('.money-stage .man .l img').addClass('move');
			$('.money-stage .man .s img').show();
			$('.money-stage .goldbox').addClass('move');
			$('.money-stage .facai').addClass('move');

			$('.money-stage .ds').html(time);

			rankInterval = setInterval(a.getPoint, 1500);
			gInterval = setInterval(function() {
				time--;
				$('.money-stage .ds').html(time);

				if(time == 1) {
					$($('.money-stage .man .p')[0]).show();
					$($('.money-stage .man .p')[1]).hide();
				}

				if(time == 0) {
					clearInterval(gInterval);
					clearInterval(rankInterval);
					a.end();
				}
			}, 1000)

		}, 5000)

	};

	//游戏结束
	a.end = function() {
		playSpecialbgm(MUSIC_MONEY_1);

		$('.money-stage .sky').removeClass('move');
		$('.money-stage .ship').removeClass('move');
		$('.money-stage .ground').removeClass('move');
		$('.money-stage .man .l img').removeClass('move');
		$('.money-stage .man .s img').hide();

		$.get(pageInfo.postUrl + 'game/endGameBefore', {
			liveId: pageInfo.roomId,
			gameCode: gameInfo.currentGame
		}, function(data) {
			if(data.code == 200) {
				$.get(pageInfo.postUrl + 'game/endGameAfter', {
					liveId: pageInfo.roomId,
					gameCode: gameInfo.currentGame
				}, function(data) {
					if(data.code == 200) {
						a.refreshRank(data.data);
						rank_layer = layer.open({
							type: 1,
							closeBtn: '0',
							title: '',
							skin: 'layui-layer-rim', // 加上边框
							area: ['40%', '70%'], // 宽高
							content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_09.png"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
							// move: '.layui-layer-content',
							shadeClose: 'true',
							resize: 'false',
						});

						//						if(pageInfo.zoom<1||pageInfo.needResize){
						//							layer.style(rank_layer, {
						//								background: 'rgba(255,255,255,0)',
						//								border: 'none',
						//								top:'100px',
						//								left:'400px',
						//								scrollbar:false,
						//								height:'550px'
						//							});
						//						}else{
						//							layer.style(rank_layer, {
						//								background: 'rgba(255,255,255,0)',
						//								border: 'none',
						//								height:'550px'
						//							});
						//						}

						layer.style(rank_layer, {
							background: 'rgba(255,255,255,0)',
							border: 'none',
							height: '550px'
						});

						for(var i = 0; i < 3; i++) {
							$('.result-headimg')[i].src = $('#money-rank .touxiang')[i].children[1].src;
							$('.result-name')[i].innerHTML = typeof($('#money-rank .username')[i].innerHTML) == undefined || $('#money-rank .username')[i].innerHTML == null ? 'Player' : $('#money-rank .username')[i].innerHTML;
							$('.result-point')[i].innerHTML = $('#money-rank .userpoint')[i].innerHTML;
						}

					}

				})

			}
		});

		//		a.getPoint(function(){
		//			
		//			$.get(pageInfo.postUrl+'game/endGame', { liveId: pageInfo.roomId, gameCode: gameInfo.currentGame }, function(data) {
		//				//数钱游戏结束
		//			});
		//			
		//			
		//		})

		gameInfo.gameIsRuning = false;

	};

	a.refreshRank = function(obj) {
		for(var i = 0; i < obj.length; i++) {
			$('#money-rank .username')[i].innerHTML = cutName(obj[i].nickname);
			$('#money-rank .userpoint')[i].innerHTML = Math.round(obj[i].score);
			$('#money-rank .user')[i].src = obj[i].headimgurl;
			//console.log(obj[i].score);
			$('#money-rank .rank-item-b')[i].dataset.point = Math.round(obj[i].score);
			//更新进度条
			$('#money-rank .progress-bar')[i].style.height = ((obj[i].score / 20000) * 350 + 10) + 'px';
		}
	};

	a.getPoint = function(callback) {
		$.get(pageInfo.postUrl + "game/countCashResult", {
			gameCode: gameInfo.currentGame,
			roomId: pageInfo.roomId
		}, function(result) {
			if(result.code == 200) {
				a.refreshRank(result.data);
				if(callback) {
					callback();
				}
			}
		}).error(function() {})
	}

})(window.moneyGame = {});

//2017-12-28
(function(game) {

	var time = 120,
		gInterval = null,
		rankInterval = null,
		aniInterval = null;

	game.init = function() {
		$('.dl-warp').empty();
		$('#huadeng-rank .progress-bar').css('height', '10px');
		$('#huadeng-rank .user').attr('src', pageInfo.path + '/static/resource/img/game/abc_03.png');
		$('#huadeng-rank .username').html('Payer');
		$('#huadeng-rank .userpoint').html('0');
		$('.huadeng-stage .moon').removeClass('move');
		time = 120;
		$('.huadeng-stage .ds').html(game.formatTime(time));

	};

	game.start = function() {

		game.init();
		gameInfo.gameIsRuning = true;
		$('.huadeng-stage .moon').addClass('move');
		game.ani(); //开始动画

		rankInterval = setInterval(game.getPoint, 1500);

	};

	game.getPoint = function() {

		$.get(pageInfo.postUrl + "game/scoreList", {
			gameCode: gameInfo.currentGame,
			liveId: pageInfo.roomId
		}, function(result) {
			if(result.code == 200) {
				game.refresh(result.data);

			}
		}).error(function() {

		})

	};

	game.refresh = function(obj) {
		for(var i = 0; i < obj.length; i++) {
			$('#huadeng-rank .username')[i].innerHTML = cutName(obj[i].nickname);
			$('#huadeng-rank .userpoint')[i].innerHTML = Math.round(obj[i].score);
			$('#huadeng-rank .user')[i].src = obj[i].headimgurl;
			//huadeng.log(obj[i].score);
			$('#huadeng-rank .rank-item-b')[i].dataset.point = Math.round(obj[i].score);
			//更新进度条
			//$('#huadeng-rank .progress-bar')[i].style.height = ((obj[i].score /320) * 350 + 10) + 'px';

			$($('#huadeng-rank .progress-bar')[i]).animate({
				height: ((obj[i].score / 310) * 185 + 10) + 'px'
			}, 500);

		}

	};

	game.end = function() {
		$('#game-end').hide();
		clearInterval(aniInterval);
		clearInterval(rankInterval);
		gameInfo.gameIsRuning = false;
		$.get(pageInfo.postUrl + 'game/endGame', {
			liveId: pageInfo.roomId,
			gameCode: gameInfo.currentGame
		}, function(data) {
			rank_layer = layer.open({
				type: 1,
				closeBtn: '0',
				title: '',
				skin: 'layui-layer-rim', // 加上边框
				area: ['40%', '70%'], // 宽高
				content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_09.png"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
				// move: '.layui-layer-content',
				shadeClose: 'true',
				resize: 'false',
			});

			layer.style(rank_layer, {
				background: 'rgba(255,255,255,0)',
				border: 'none',
				height: '550px'
			});

			for(var i = 0; i < 3; i++) {
				$('.result-headimg')[i].src = $('#huadeng-rank .touxiang')[i].children[1].src;
				$('.result-name')[i].innerHTML = typeof($('#huadeng-rank .username')[i].innerHTML) == undefined || $('#huadeng-rank .username')[i].innerHTML == null ? 'Player' : $('#huadeng-rank .username')[i].innerHTML;
				$('.result-point')[i].innerHTML = $('#huadeng-rank .userpoint')[i].innerHTML;
			}

		})
	};

	game.random = function(n, m) {
		var random = Math.floor(Math.random() * (m - n)) + n;
		return random;
	};

	game.formatTime = function(result) {
		var h = Math.floor(result / 3600);
		var m = Math.floor((result / 60 % 60));
		var s = Math.floor((result % 60));

		m = m < 10 ? "0" + m : m;
		s = s < 10 ? "0" + s : s;
		return result = m + ":" + s + "";
	};

	game.ani = function() {
		//每隔一秒 产生5个灯笼

		function _removeImg(img, id) {
			console.log(id);
			setTimeout(function() {
				$(img).remove();
			}, 23000);

		}

		aniInterval = setInterval(function() {
			$('.huadeng-stage .ds').html(game.formatTime(time));
			if(time == 0) {
				game.end();
				return;
			} else {
				var imgs = [];
				for(var i = 0; i < 3; i++) {
					var id = 'dl-' + time + '-' + i;
					var img = document.createElement('img');
					img.id = 'dl-' + time + '-' + i;
					img.src = pageInfo.path + 'static/resource/img/huadeng/dl.png';
					img.style.position = 'absolute';
					img.style.bottom = '-121px';
					img.style.left = game.random(700, 1180) + 'px';
					imgs.push(img);
					document.querySelector('.dl-warp').appendChild(img);
					_removeImg(img);
				}

				setTimeout(function() {
					for(var i = 0; i < 3; i++) {
						imgs[i].style.transition = 'ease-out,' + game.random(10, 20) + 's,' + 'infinite';
						imgs[i].style.transform = 'translate(' + game.random(-280, 280) + 'px,-879px) scale(0.6)';
						imgs[i].style.transitionDelay = game.random(0, 3) + 's';

					}
				}, 50);
				time--
			}

		}, 1000);

	}

})(window.huadengGame = {});

(function(app) {
	//礼物模块 统一	

	//思域超跑
	var ferrari = {
		time: 10000, //礼物动画效果时间
		ui: {
			warp: document.querySelector(".gift-ferrari"),
			car: document.querySelector(".ferrari"),
			light: document.querySelector(".ferrari-light"),
			halo: document.querySelector(".ferrari-halo"),
			stage: document.querySelector(".gift-ferrari"),
		},

		start: function() {
			var that = this;
			that.ui.warp.style.display = 'block';
			console.log(that.ui.car);
			snabbt(that.ui.car, {
				fromOpacity: 1,
				opacity: 1,
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				fromPosition: [0, 0, 0],
				position: [1200, 600, 0],
				duration: 1000,
				easing: 'ease',
				allDone: function() {
					console.log('car move');
					that.ui.car.classList.add('move');
				}
			});

			snabbt(that.ui.light, {
				fromPosition: [0, 0, 0],
				position: [1200, 530, 0],
				duration: 500,
				easing: 'ease',
				delay: 1000
			}).snabbt({
				position: [1150, 500, 0],
				duration: 200,
			}).snabbt({
				position: [1200, 530, 0],
				duration: 200,
			}).snabbt({
				position: [1150, 500, 0],
				duration: 200,
			}).snabbt({
				position: [1200, 530, 0],
				duration: 200,
			}).snabbt({
				position: [1150, 500, 0],
				duration: 200,
			}).snabbt({
				position: [1200, 530, 0],
				duration: 200,
			}).snabbt({
				position: [2850, 830, 0],
				duration: 200,
			});

			snabbt(that.ui.halo, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				delay: 2900,
				duration: 500,
			}).snabbt({
				fromScale: [1.5, 1.5],
				scale: [0.8, 0.8],
				duration: 500,
			}).snabbt({
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				duration: 500,
			}).snabbt({
				fromScale: [1.5, 1.5],
				scale: [0.8, 0.8],
				duration: 500,
			}).snabbt({
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				duration: 500,
			}).snabbt({
				fromScale: [1.5, 1.5],
				scale: [0.8, 0.8],
				duration: 500,
			}).snabbt({
				fromOpacity: 0,
				opacity: 0,
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				duration: 500,
				complete: function() {
					console.log('car stop');
					that.ui.car.classList.remove('move');
					snabbt(that.ui.car, {
						fromOpacity: 1,
						opacity: 0,
						fromScale: [1.5, 1.5],
						scale: [0.8, 0.8],
						fromPosition: [1200, 600, 0],
						position: [2400, 600, 0],
						duration: 1000,
						easing: 'ease',
						complete: function() {
							that.ui.warp.style.display = 'none';
						}
					});
				}
			})
		}
	}

	var c919 = {
		ui: {
			warp: document.querySelector(".gift-cplane"),
			yanhua1: document.querySelector(".yanhua1"),
			yanhua2: document.querySelector(".yanhua2"),
			yanhua3: document.querySelector(".yanhua3"),
			yanhua4: document.querySelector(".yanhua4"),
			plane: document.querySelector(".cplane"),
			cloud: document.querySelector(".ccloud")
		},

		start: function() {
			var that = this;
			that.ui.warp.style.display = 'block';
			snabbt(that.ui.plane, {
				fromScale: [0.6, 0.6],
				scale: [1, 1],
				fromPosition: [0, 0, 0],
				position: [-900, 0, 0],
				duration: 1000,
			}).snabbt({
				fromPosition: [-900, 0, 0],
				position: [-1400, 0, 0],
				duration: 5000,
				easing: 'ease',
			}).snabbt({
				fromPosition: [-1400, 0, 0],
				position: [-2800, 0, 0],
				duration: 1500,
				easing: 'ease',
				complete: function() {
					that.ui.warp.style.display = 'none';
				}
			});

			snabbt(that.ui.cloud, {
				fromPosition: [0, 0, 0],
				position: [1400, 0, 0],
				duration: 1000,
			}).snabbt({
				fromPosition: [1400, 0, 0],
				position: [1800, 0, 0],
				duration: 5000,
				easing: 'ease',
			}).snabbt({
				fromPosition: [1800, 0, 0],
				position: [3500, 0, 0],
				duration: 1500,
				easing: 'ease',
			});

			snabbt(that.ui.yanhua1, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
				delay: '2000'
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromOpacity: 1,
				opacity: 0,
				duration: 700,
				easing: 'easeOut',
			});

			snabbt(that.ui.yanhua2, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
				delay: '2500'
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromOpacity: 1,
				opacity: 0,
				duration: 700,
				easing: 'easeOut',
			})

			snabbt(that.ui.yanhua3, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
				delay: '1800'
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromOpacity: 1,
				opacity: 0,
				duration: 700,
				easing: 'easeOut',
			})

			snabbt(that.ui.yanhua4, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
				delay: '2800'
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromOpacity: 1,
				opacity: 0,
				duration: 700,
				easing: 'easeOut',
			})

		}

	};

	var plane = {
		ui: {},
		start: function() {
			document.querySelector(".gift-plane").style.display = 'block';
			//plane 动画
			snabbt(document.querySelector(".gift-plane .plane"), {
				fromOpacity: 1,
				opacity: 1,
				fromPosition: [0, 0, 0],
				position: [-1400, 250, 0],
				duration: 5000,
				//					easing: 'ease',
			}).snabbt({
				fromScale: [1, 1],
				scale: [1.2, 1.2],
				fromPosition: [-1400, 250, 0],
				position: [-1650, 250, 0],
				duration: 3000,
				//					easing: 'ease',
			}).snabbt({
				duration: 1000,
			}).snabbt({
				fromScale: [1.2, 1.2],
				scale: [1, 1],
				fromPosition: [-1650, 250, 0],
				position: [-3000, 350, 0],
				duration: 1000,
				complete: function() {
					document.querySelector(".gift-plane").style.display = 'none';
				}
			});

			snabbt(document.querySelector(".gift-plane .ground"), {
				fromOpacity: 0,
				opacity: 1,
				duration: 1000
			}).snabbt({
				fromOpacity: 1,
				opacity: 0,
				duration: 1000,
				delay: 8000
			});

		}
	}

	var gift = app.gift = {
		ferrari: ferrari,
		c919: c919,
		plane: plane
	};
})(window.hdAPP || (window.hdAPP = {}));//简易的图片轮播  JS
	var hslider = {
		background_timer: null,
		current: 0,
		next: 0,
		time:8000,
		init: function() {
			var that = this;
			//$('.slider-item').css('height', window.innerHeight + 'px');
			$('.slider-item')[0].style.display = 'block';
			
			if($('.slider-item').length>1){
				hslider.background_timer = setInterval(hslider.mySlider, hslider.time);
			}
		},
		mySlider: function() {
			//console.log(hslider.current);
			for(var i = 0; i < $('.slider-item').length; i++) {
				if(hslider.current == i) {
					if(hslider.current == $('.slider-item').length - 1) {
						hslider.next = 0;
						hslider.silder(hslider.current, hslider.next);
						hslider.current = 0;
					} else {
						hslider.next += 1;
						hslider.silder(hslider.current, hslider.next);
						hslider.current++;
					}
					break;
				}
			}

		},
		silder: function(a,b) {
			$('.slider-item')[a].classList.remove('fadeIn');
			$('.slider-item')[a].classList.add('fadeOut');
			$('.slider-item')[b].classList.remove('fadeOut');
			$('.slider-item')[b].classList.add('fadeIn')
		},
		stop: function() {
			clearInterval(hslider.background_timer);
		},
		start: function() {
			hslider.background_timer = setInterval(hslider.mySlider, hslider.time);
		}
	}

	//hslider.init();
	
	$.get(pageInfo.postUrl + "backgroundController/listBackgroundImg/"+pageInfo.roomId, {}, function(data) {
		if(data.success){
			//alert('123');
			//$('#sliderWarp').
			//alert(typeof(data.data)!=undefined)
			if(typeof(data.data)!='undefined'){
				if(data.data.length>0){
					$('#sliderWarp').empty();
					$('#sliderWarp')[0].style.back
					for(var i = 0;i<data.data.length;i++){
						$('#sliderWarp').append('<div class="slider-item fadeOut" style="background-image: url('+data.data[i]+');"></div>')
					}
					$('#sliderWarp')[0].children[0].classList.remove('fadeOut');
					$('#sliderWarp')[0].children[0].classList.add('fadeIn');
					document.getElementById("sliderWarp").style.background="";
					hslider.init();
				}
			}
			
		}else{};
		
	}).error(function(){
		
	});


function resizeHtml() {
			$('body').css('margin-top','0');
			setTimeout(function(){
				console.log('time resize');
				/*缩放系数   1280*720 为基础*/
				pageInfo.zoom = window.innerWidth/1280 >window.innerHeight/720?window.innerHeight/720:window.innerWidth/1280
				/*缩放系数   1920*1080 为基础*/
				pageInfo.zooma = window.innerWidth/1920 >window.innerHeight/1080?window.innerHeight/1080:window.innerWidth/1920
				pageInfo.needResize = false;
				if((window.innerHeight/window.innerWidth).toFixed(2)>(9/16).toFixed(2)){
					pageInfo.needResize = true;
				}	
				
				$('body').css('zoom','1');
				
				document.body.style.height = window.innerHeight + 'px';
				document.body.style.width = window.innerWidth+'px';
				$('.slider-item').css('height', window.innerHeight + 'px');
				$('.module').css('height', window.innerHeight + 'px')
				
				
				/*游戏  resize*/
				$('.leda').css('margin-top',(window.innerHeight - 140 - $('.leda').height())/ 2 + 'px');
				
				//$('#erweima').css('margin-top',(window.innerHeight - 140 - $('.leda').height()) / 2+ $('.game-wait-people').height() + 'px');
				$('#erweima').css('margin-top',(window.innerHeight - 140 - $('#erweima').height()) / 2);
				
				/*游戏  resize END*/

				/*摇一摇  resize*/
				$('.shakegame .content').css({
					"height" : window.innerHeight + 'px',
					"width" : window.innerWidth + 'px',
					"background-color" : "rgb(248,217,156)"
				})

				$('.husband-warp').css({
					"left" : '1110px'
				})
				var _w = window.innerWidth - $('.rank-warp li').width() * 10
				if (_w > 0) {
					$('.rank-warp li:first-child')
							.css('margin-left', _w / 2 + 'px')
				}
				/*摇一摇  resize*/

				/*抽奖 resize*/
				$('.luckdraw').css('zoom', pageInfo.zoom);
				$('.lottery-result').css('zoom',pageInfo.zooma);
				/*抽奖 resize*/
				
				/*guess resize*/
				$('.guess-warp').css('zoom', pageInfo.zooma);
				/*guess resize*/
				
				$('#moneygame').css('zoom',pageInfo.zooma);
				
				$('.tip-shake').css('zoom',pageInfo.zooma);
				
				/*3D签到*/
				$('#bigCard').css('margin-left', (window.innerWidth - $('#bigCard').width()) / 2 + 'px');
				$('#bigCard').css('margin-top', (window.innerHeight - $('#bigCard').height()) / 2 + 'px');
			
				/*ship resize*/
				$('#shipGift')[0].style.zoom =  pageInfo.zooma;
				
				$('.gift-huajia')[0].style.zoom =  pageInfo.zooma;
				$('.gift-sansheng')[0].style.zoom =  pageInfo.zooma;
				
				/*chat gx*/
				document.getElementById("cw").style.zoom = pageInfo.zooma;
				
				/*bahe resize*/
				$('#mk-bahe').css('zoom',pageInfo.zooma);
				$('#mk-bahemain').css('zoom',pageInfo.zooma);
				
				/*countdown resize*/
//				$('.wrappercount').css('zoom',pageInfo.zooma);
				$('#mgamecountdown').css('zoom',pageInfo.zooma);
//				$('#mgamecountdown').css('left',685*pageInfo.zooma+'px');
				
				$('.red-warp').css('zoom',pageInfo.zooma);
				
				if(pageInfo.zoom<1||pageInfo.needResize){
					$('body').css('width','1280px');
					$('body').css('height','720px');
					$('body').css('zoom',pageInfo.zoom);
					$('.slider-item').css('height', '720px');
					
					$('body').css('margin-top',((window.innerHeight-720*pageInfo.zoom)/pageInfo.zoom)/2+'px');
				
					$('.signin3D').css('zoom','1');
					$('.module').css('height', '720px');
					
					/*游戏  resize*/
					$('.leda').css('margin-top','0px');
					//$('#erweima').css('margin-top','0px');
					
					$('#erweima').css('margin-top',(window.innerHeight - 140 - $('#erweima').height()) / 2);
					
					
					/*游戏  resize END*/
					
					/*摇一摇  resize*/
					$('.shakegame .content').css({
						"height" : '720px',
						"width" :'1280px',
						"background-color" : "rgb(248,217,156)"
					})

					$('.husband-warp').css({
						"left" : '1110px'
					})
					/*var _w = 1280 - $('.rank-warp li').width() * 10
					if (_w > 0) {
						$('.rank-warp li:first-child').css('margin-left', _w / 2 + 'px')
					}*/
					$('.rank-warp li:first-child').css('margin-left', '0px');
					/*摇一摇  resize*/
					
					/*抽奖 resize*/
					$('.luckdraw').css('zoom', '1');
					$('.lottery-result').css('zoom',1280/1920);
					/*抽奖 resize END*/
					
					/*guess resize*/
					$('.guess-warp').css('zoom', 1280/1920);
					/*guess resize*/
					$('#moneygame').css('zoom', 1280/1920);
					
					$('.tip-shake').css('zoom',1280/1920);
					
					document.getElementById("cw").style.zoom = '0.63';
					
					$('#cw').css({
						'zoom':'0.63',
						//'top':'100px'
					})
					
					/*ship resize*/
					$('#shipGift')[0].style.zoom =  1280/1920;
					
					$('.gift-huajia')[0].style.zoom =  1280/1920;
					$('.gift-sansheng')[0].style.zoom = 1280/1920;
					
					
					/*bahe resize*/
					$('#mk-bahe').css('zoom',1280/1920);
					$('#mk-bahemain').css('zoom',1280/1920);
					
					/*countdown resize*/
					$('#mgamecountdown').css('zoom',1280/1920);
//					$('#mgamecountdown').css('left',685*(1280/1920)+'px');
					
					/*red-warp resize*/
					$('.red-warp').css('zoom',1280/1920);
				}
			},50)	
		}
		
		//window.addEventListener('resize', resizeHtml);
		
		
		var erweimaLayer;
		
		function erweima() {
			if(erweimaLayer){
				layer.close(erweimaLayer);
				erweimaLayer = null;
			}else{
				//页面层
				var _offset = 'r';
//				if(pageInfo.needResize){
//					
//					_offset = ["300px",1280+"px"]
//				}else{
//					_offset="r"
//				}
				
				erweimaLayer=layer.open({
					type : 1,
					title : '',
					
					area: [400 * pageInfo.scale + 'px', 400 * pageInfo.scale + 'px'], //宽高
					content : '<img src="'+pageInfo.qrcodeUrl+'" style="height:100%;width:100%"/>',
					shade : 0,
					move : '.layui-layer-content',
					offset: _offset,
					end:function(){erweimaLayer=null}
				});
				
//				if(pageInfo.zoom<1||pageInfo.needResize){
//					layer.style(erweimaLayer,{
//						zoom:pageInfo.zoom
//					});
//				}
			}
		}

		layer.ready(function() {
			layer.msg('欢迎来到嗨动平台');
		});
		
		
		
		var gift_layer =null;
		$('#choujiangliwu').on('click',function(){
		
			 gift_layer=layer.open({
				type: 1,
				title: '',
//		   		skin: 'layui-layer-rim', //加上边框
				area: ['350px', '350px'], //宽高
				content:"<img src="+pageInfo.path+"static/resource/img/prize.png />",
				resize: 'false',
				shadeClose: 'true',
			});
			
			layer.style(gift_layer, {
				background: 'rgba(0,0,0,0.5)',
				border: 'none'
			});
			
		});	
		
		var networkInterval = setInterval(function(){
			if(!navigator.onLine){
				layer.alert('网络已经断开,请刷新页面', {
					  title:'警告',
					  icon: 2,
					  skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
				})
			}
		},20000);
		
	    
		function _resize() {

			  setTimeout(function () {
			    var windowH = window.innerHeight;
			    var windowW = window.innerWidth;
			    document.querySelector('body').style.height = windowH + 'px';
			    document.querySelector('body').style.width = windowW + 'px';
			 

			    //base 1920 1080

			    var needScale1 = windowH / 1080;
			    var needScale2 = windowW / 1920;
			   
			    if (needScale1 < needScale2) {
			      document.getElementById("hdApp").style.transform = "scale(" + needScale1 + ") translateX(" + ((windowW - 1920 * needScale1) / 2) / needScale1 + "px)";
			      pageInfo.scale = needScale1;
			      pageInfo.x = ((windowW - 1920 * needScale1) / 2) / needScale1 ;
			    } else {
			      document.getElementById("hdApp").style.transform = "scale(" + needScale2 + ") translateY(" + ((windowH - 1080 * needScale2) / 2) / needScale2 + "px)";
			      pageInfo.scale = needScale2;
			      pageInfo.y = ((windowH - 1080 * needScale2) / 2) / needScale2 ;
			    }
			    
				$('.shakegame .content').css({
					"height" : '1080px',
					"width" : '1920pxpx',
					"background-color" : "rgb(248,217,156)"
				})

				$('.husband-warp').css({
					"left" : '1110px'
				})
				
				
			
				var _w = 1920 - $('.rank-warp li').width() * 10
				if (_w > 0) {
					$('.rank-warp li:first-child')
					.css('margin-left', _w / 2 + 'px')
				}
			    
//			    $('.leda').css('margin-top',(window.innerHeight - 140 - $('.leda').height())/ 2 + 'px');
//								//$('#erweima').css('margin-top',(window.innerHeight - 140 - $('.leda').height()) / 2+ $('.game-wait-people').height() + 'px');
//				$('#erweima').css('margin-top',(window.innerHeight - 140 - $('#erweima').height()) / 2);

			  });
			}

			window.onresize = _resize;
		
			_resize();