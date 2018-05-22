//3D签到 three.js
(function(app) {
	var objects = [],
		targets = {
			table: [],
			sphere: [],
			helix: [],
			grid: [],
			random: []
		},
		camera,
		scene,
		renderer,
		controls;
	var personArray = new Array;

	var signList = {
		status: false,
		queue: []
	};

	var _index = [];

	// 生成虚拟数据 基本版   500个头像
	for(var i = 0; i < 507; i++) {
		personArray.push({
			image: pageInfo.path + 'static/resource/img/timg.png'
		});
		_index.push(i);
		_index.sort(function() {
			return 0.5 - Math.random()
		})
	}

	var table = new Array;
	for(var i = 0; i < personArray.length; i++) {
		table[i] = new Object();
		if(i < personArray.length) {
			table[i] = personArray[i];
			table[i].src = personArray[i].thumb_image;
		}
		table[i].p_x = i % 39 + 1;
		table[i].p_y = Math.floor(i / 39) + 1;
	}

	//4种效果

	//	init();
	//	animate();
	//
	//	listenSign();
	function listenSign() {
		observe(signList, function(name, value, old) {
			//	console.log(name + "__" + value + "__" + old);
			//	console.log(JSON.stringify(value))
			if(name == 'Array-unshift') {
				if(!signList.status && signList.queue.length > 0) {
					showSignIn();
				}
			}
		})

		function showSignIn() {
			if(signList.queue.length <= 0) {
				signList.status = false;
				return;
			} else {
				signList.status = true;
			}

			var user = signList.queue.pop();

			if(_index.length == 0) {
				for(var i = 0; i < 500; i++) {
					_index.push(i)
				}
				_index.sort(function() {
					return 0.5 - Math.random()
				})
			}

			var index = _index.pop();
			//$('.card').eq(index).find('img').attr('src', pageInfo.picHost + user.headimg);
			//$('.card').removeClass('imgOut').addClass('imgIn');
			$('#newbigcard').find('img').attr('src', user.headimg)
			$('#newbigcard').find('span').html(user.username + '</br>' + user.wish);
			$('#newbigcard').addClass('signani');

			$('.new-card img')[index].src = user.headimg;

			setTimeout(function() {
				$('#newbigcard').removeClass('signani');
				setTimeout(showSignIn, 1000);
			}, 4000)

		}

	}

	function init() {
		//camera = new THREE.PerspectiveCamera(40, window.innerWidth / window.innerHeight, 1, 10000);
		camera = new THREE.PerspectiveCamera(40, 1920 / 1080, 1, 10000);
		camera.position.z = 6000;
		scene = new THREE.Scene();

		// table
		for(var i = 0; i < table.length; i++) {

			var element = document.createElement('div');
			//element.className = 'element';
			//element.style.backgroundColor = 'rgba(0,127,127,' + ( Math.random() * 0.5 + 0.25 ) + ')';
			element.className = 'card new-card';

			var img = document.createElement('img');
			img.src = table[i].image;
			element.appendChild(img);

			var object = new THREE.CSS3DObject(element);
			object.position.x = Math.random() * 4000 - 2000;
			object.position.y = Math.random() * 4000 - 2000;
			object.position.z = Math.random() * 4000 - 2000;
			scene.add(object);

			var object1 = new THREE.Object3D();
			object1.position.x = Math.random() * 4000 - 2000;
			object1.position.y = Math.random() * 4000 - 2000;
			object1.position.z = Math.random() * 4000 - 2000;

			targets.random.push(object1);

			objects.push(object);

			// 表格需要坐标进行排序的
			var object = new THREE.Object3D();
			object.position.x = (table[i].p_x * 140) - 2800; //1330
			object.position.y = -(table[i].p_y * 180) + 1200; // 990
			targets.table.push(object);
		}

		// sphere
		var vector = new THREE.Vector3();
		var spherical = new THREE.Spherical();

		for(var i = 0, l = objects.length; i < l; i++) {

			var phi = Math.acos(-1 + (2 * i) / l);
			var theta = Math.sqrt(l * Math.PI) * phi;

			var object = new THREE.Object3D();

			spherical.set(1500, phi, theta);

			object.position.setFromSpherical(spherical);

			vector.copy(object.position).multiplyScalar(2);

			object.lookAt(vector);

			targets.sphere.push(object);

		}

		// helix
		var vector = new THREE.Vector3();
		var cylindrical = new THREE.Cylindrical();

		for(var i = 0, l = objects.length; i < l; i++) {

			var theta = i * 0.175 + Math.PI;
			var y = -(i * 5) + 1250;

			var object = new THREE.Object3D();

			// 参数一 圈的大小 参数二 左右间距 参数三 上下间距
			cylindrical.set(1500, theta, y);

			object.position.setFromCylindrical(cylindrical);

			vector.x = object.position.x * 2;
			vector.y = object.position.y;
			vector.z = object.position.z * 2;

			object.lookAt(vector);

			targets.helix.push(object);
		};

		// grid
		for(var i = 0; i < objects.length; i++) {

			var object = new THREE.Object3D();

			object.position.x = ((i % 5) * 400) - 800; // 400 图片的左右间距  800 x轴中心店
			object.position.y = (-(Math.floor(i / 5) % 5) * 300) + 500; // 500 y轴中心店
			object.position.z = (Math.floor(i / 25)) * 200 - 1800; // 300调整 片间距 800z轴中心店

			targets.grid.push(object);

		}

		//渲染
		renderer = new THREE.CSS3DRenderer();
		//		renderer.setSize(window.innerWidth, window.innerHeight);
		renderer.setSize(1920, 1080);
		renderer.domElement.style.position = 'absolute';
		document.getElementById('3dcontainer').appendChild(renderer.domElement);

		// 鼠标控制
		controls = new THREE.TrackballControls(camera, renderer.domElement);
		controls.rotateSpeed = 0.5;
		controls.minDistance = 500;
		controls.maxDistance = 6000;
		controls.addEventListener('change', render);

		var ini = 0;
		setInterval(function() {
			//ini = ini >= 4 ? 0 : ini;
			//++ini;
			console.log('ini=' + ini);

			switch(ini) {
				case 0:
					transform(targets.table, 1000);
					break;
				case 1:
					transform(targets.sphere, 1000);
					break;
				case 2:
					transform(targets.helix, 1000);
					break;
				case 3:
					transform(targets.grid, 1000);
					break;
//				case 4:
//					transform(targets.random, 1000);
					break;
			}

			ini = ini >= 3 ? 0 : ini + 1;

		}, 20000);

	}

	function transform(targets, duration) {

		TWEEN.removeAll();

		for(var i = 0; i < objects.length; i++) {

			var object = objects[i];
			var target = targets[i];

			new TWEEN.Tween(object.position)
				.to({
					x: target.position.x,
					y: target.position.y,
					z: target.position.z
				}, Math.random() * duration + duration)
				.easing(TWEEN.Easing.Exponential.InOut)
				.start();

			new TWEEN.Tween(object.rotation)
				.to({
					x: target.rotation.x,
					y: target.rotation.y,
					z: target.rotation.z
				}, Math.random() * duration + duration)
				.easing(TWEEN.Easing.Exponential.InOut)
				.start();

		}

		new TWEEN.Tween(this)
			.to({}, duration * 2)
			.onUpdate(render)
			.start();

	}

	function onWindowResize() {
		camera.aspect = window.innerWidth / window.innerHeight;
		camera.updateProjectionMatrix();
		renderer.setSize(window.innerWidth, window.innerHeight);
		render();
	}

	function animate() {
		// 让场景通过x轴或者y轴旋转  & z
		//scene.rotation.x += 0.011;
		scene.rotation.y += 0.02;

		requestAnimationFrame(animate);

		TWEEN.update();

		controls.update();
		// 渲染循环
		render();

	};

	function render() {
		renderer.render(scene, camera);
	};

	function putUserToTable(obj) {
		//将用户放入签到画面中
		if(_index.length == 0) {
			for(var i = 0; i < 500; i++) {
				_index.push(i)
			}
			_index.sort(function() {
				return 0.5 - Math.random()
			})
		}

		for(var i = 0; i < obj.length; i++) {
			var index = _index.pop();
			$('.new-card img')[index].src = obj[i].reheadimgurl;
		}

	}

	var isFirst = false;

	var sign = app.sign = {

		init: function() {
			if(isFirst) {
				return;
			}
			isFirst = true;
			init();
			animate();
			listenSign();
			
			console.log('--sign1--');
			if(userJoinedArr.length == 0) {
				$.get(gameHost + "/reportController/report/" + pageInfo.roomId, function(data) {
					$('#current-people').html(data.data.length + '');
					userJoinedArr = data.data;
					$('.card').find('img').attr('onerror', 'errorImg(this)');
					$('.bigCard').find('img').attr('onerror', 'errorImg(this)');
					//putuser_3d(userJoinedArr);
					putUserToTable(userJoinedArr);

				}, 'json').error(function() {
					$('.card').find('img').attr('onerror', 'errorImg(this)');
					$('.bigCard').find('img').attr('onerror', 'errorImg(this)');
					//putuser_3d(userJoinedArr);
					putUserToTable(userJoinedArr);
				})
			} else {
				//putuser_3d(userJoinedArr);
				console.log('--sign2--');
				putUserToTable(userJoinedArr);
				$('.card').find('img').attr('onerror', 'errorImg(this)');
				$('.bigCard').find('img').attr('onerror', 'errorImg(this)');
			}

		},

		add: function() {
			signList.queue.unshift({
				headimg: 'http://www.chinanews.com/cr/2013/1122/796252081.jpg'
			});
		},

		addToQueue: function(obj) {
			signList.queue.unshift({
				headimg: obj.photoUrl,
				username: obj.userName,
				wish: obj.wish
			});
		}
	};

})(window.hdAPP || (window.hdAPP = {}))