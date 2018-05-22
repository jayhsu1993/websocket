//溢辉集团
(function(app) {
	var objects = [],
		targets = {
			table: [],
			sphere: [],
			helix: [],
			grid: [],
			random: [],
			logo: []
		},
		camera,
		scene,
		renderer,
		controls;
	var personArray = new Array;

	var logoPostion = '{"175":{"x":-3080,"y":780},"176":{"x":-2950,"y":780},"179":{"x":-2560,"y":780},"180":{"x":-2430,"y":780},"188":{"x":-1390,"y":780},"189":{"x":-1260,"y":780},"195":{"x":-480,"y":780},"196":{"x":-350,"y":780},"201":{"x":300,"y":780},"202":{"x":430,"y":780},"203":{"x":560,"y":780},"204":{"x":690,"y":780},"205":{"x":820,"y":780},"206":{"x":950,"y":780},"207":{"x":1080,"y":780},"208":{"x":1210,"y":780},"209":{"x":1340,"y":780},"210":{"x":1470,"y":780},"211":{"x":1600,"y":780},"212":{"x":1730,"y":780},"213":{"x":1860,"y":780},"220":{"x":2770,"y":780},"221":{"x":2900,"y":780},"222":{"x":3030,"y":780},"232":{"x":-3080,"y":650},"233":{"x":-2950,"y":650},"236":{"x":-2560,"y":650},"237":{"x":-2430,"y":650},"245":{"x":-1390,"y":650},"246":{"x":-1260,"y":650},"249":{"x":-870,"y":650},"250":{"x":-740,"y":650},"251":{"x":-610,"y":650},"252":{"x":-480,"y":650},"253":{"x":-350,"y":650},"254":{"x":-220,"y":650},"255":{"x":-90,"y":650},"256":{"x":40,"y":650},"258":{"x":300,"y":650},"259":{"x":430,"y":650},"260":{"x":560,"y":650},"261":{"x":690,"y":650},"262":{"x":820,"y":650},"263":{"x":950,"y":650},"264":{"x":1080,"y":650},"265":{"x":1210,"y":650},"266":{"x":1340,"y":650},"267":{"x":1470,"y":650},"268":{"x":1600,"y":650},"269":{"x":1730,"y":650},"270":{"x":1860,"y":650},"276":{"x":2640,"y":650},"277":{"x":2770,"y":650},"278":{"x":2900,"y":650},"279":{"x":3030,"y":650},"280":{"x":3160,"y":650},"286":{"x":-3470,"y":520},"287":{"x":-3340,"y":520},"288":{"x":-3210,"y":520},"289":{"x":-3080,"y":520},"290":{"x":-2950,"y":520},"291":{"x":-2820,"y":520},"292":{"x":-2690,"y":520},"293":{"x":-2560,"y":520},"294":{"x":-2430,"y":520},"295":{"x":-2300,"y":520},"296":{"x":-2170,"y":520},"297":{"x":-2040,"y":520},"300":{"x":-1650,"y":520},"301":{"x":-1520,"y":520},"302":{"x":-1390,"y":520},"303":{"x":-1260,"y":520},"304":{"x":-1130,"y":520},"305":{"x":-1000,"y":520},"306":{"x":-870,"y":520},"307":{"x":-740,"y":520},"308":{"x":-610,"y":520},"309":{"x":-480,"y":520},"310":{"x":-350,"y":520},"311":{"x":-220,"y":520},"312":{"x":-90,"y":520},"313":{"x":40,"y":520},"332":{"x":2510,"y":520},"333":{"x":2640,"y":520},"334":{"x":2770,"y":520},"336":{"x":3030,"y":520},"337":{"x":3160,"y":520},"338":{"x":3290,"y":520},"343":{"x":-3470,"y":390},"344":{"x":-3340,"y":390},"345":{"x":-3210,"y":390},"346":{"x":-3080,"y":390},"347":{"x":-2950,"y":390},"348":{"x":-2820,"y":390},"349":{"x":-2690,"y":390},"350":{"x":-2560,"y":390},"351":{"x":-2430,"y":390},"352":{"x":-2300,"y":390},"353":{"x":-2170,"y":390},"354":{"x":-2040,"y":390},"357":{"x":-1650,"y":390},"358":{"x":-1520,"y":390},"359":{"x":-1390,"y":390},"360":{"x":-1260,"y":390},"361":{"x":-1130,"y":390},"362":{"x":-1000,"y":390},"363":{"x":-870,"y":390},"364":{"x":-740,"y":390},"369":{"x":-90,"y":390},"370":{"x":40,"y":390},"372":{"x":300,"y":390},"373":{"x":430,"y":390},"374":{"x":560,"y":390},"375":{"x":690,"y":390},"376":{"x":820,"y":390},"377":{"x":950,"y":390},"379":{"x":1210,"y":390},"380":{"x":1340,"y":390},"381":{"x":1470,"y":390},"382":{"x":1600,"y":390},"383":{"x":1730,"y":390},"384":{"x":1860,"y":390},"387":{"x":2250,"y":390},"388":{"x":2380,"y":390},"389":{"x":2510,"y":390},"395":{"x":3290,"y":390},"396":{"x":3420,"y":390},"397":{"x":3550,"y":390},"403":{"x":-3080,"y":260},"404":{"x":-2950,"y":260},"407":{"x":-2560,"y":260},"408":{"x":-2430,"y":260},"415":{"x":-1520,"y":260},"416":{"x":-1390,"y":260},"418":{"x":-1130,"y":260},"420":{"x":-870,"y":260},"421":{"x":-740,"y":260},"422":{"x":-610,"y":260},"423":{"x":-480,"y":260},"424":{"x":-350,"y":260},"425":{"x":-220,"y":260},"426":{"x":-90,"y":260},"427":{"x":40,"y":260},"429":{"x":300,"y":260},"430":{"x":430,"y":260},"431":{"x":560,"y":260},"432":{"x":690,"y":260},"433":{"x":820,"y":260},"434":{"x":950,"y":260},"436":{"x":1210,"y":260},"437":{"x":1340,"y":260},"438":{"x":1470,"y":260},"439":{"x":1600,"y":260},"440":{"x":1730,"y":260},"441":{"x":1860,"y":260},"443":{"x":2120,"y":260},"444":{"x":2250,"y":260},"445":{"x":2380,"y":260},"447":{"x":2640,"y":260},"448":{"x":2770,"y":260},"449":{"x":2900,"y":260},"450":{"x":3030,"y":260},"451":{"x":3160,"y":260},"453":{"x":3420,"y":260},"454":{"x":3550,"y":260},"455":{"x":3680,"y":260},"461":{"x":-2950,"y":130},"462":{"x":-2820,"y":130},"468":{"x":-2040,"y":130},"472":{"x":-1520,"y":130},"475":{"x":-1130,"y":130},"476":{"x":-1000,"y":130},"479":{"x":-610,"y":130},"480":{"x":-480,"y":130},"486":{"x":300,"y":130},"487":{"x":430,"y":130},"490":{"x":820,"y":130},"491":{"x":950,"y":130},"493":{"x":1210,"y":130},"494":{"x":1340,"y":130},"497":{"x":1730,"y":130},"498":{"x":1860,"y":130},"506":{"x":2900,"y":130},"517":{"x":-3080,"y":0},"518":{"x":-2950,"y":0},"519":{"x":-2820,"y":0},"521":{"x":-2560,"y":0},"522":{"x":-2430,"y":0},"524":{"x":-2170,"y":0},"525":{"x":-2040,"y":0},"528":{"x":-1650,"y":0},"529":{"x":-1520,"y":0},"531":{"x":-1260,"y":0},"532":{"x":-1130,"y":0},"535":{"x":-740,"y":0},"537":{"x":-480,"y":0},"538":{"x":-350,"y":0},"539":{"x":-220,"y":0},"540":{"x":-90,"y":0},"541":{"x":40,"y":0},"543":{"x":300,"y":0},"544":{"x":430,"y":0},"545":{"x":560,"y":0},"547":{"x":820,"y":0},"548":{"x":950,"y":0},"550":{"x":1210,"y":0},"551":{"x":1340,"y":0},"552":{"x":1470,"y":0},"554":{"x":1730,"y":0},"555":{"x":1860,"y":0},"558":{"x":2250,"y":0},"559":{"x":2380,"y":0},"560":{"x":2510,"y":0},"561":{"x":2640,"y":0},"562":{"x":2770,"y":0},"563":{"x":2900,"y":0},"564":{"x":3030,"y":0},"565":{"x":3160,"y":0},"566":{"x":3290,"y":0},"567":{"x":3420,"y":0},"568":{"x":3550,"y":0},"573":{"x":-3210,"y":-130},"574":{"x":-3080,"y":-130},"575":{"x":-2950,"y":-130},"578":{"x":-2560,"y":-130},"579":{"x":-2430,"y":-130},"580":{"x":-2300,"y":-130},"581":{"x":-2170,"y":-130},"585":{"x":-1650,"y":-130},"586":{"x":-1520,"y":-130},"587":{"x":-1390,"y":-130},"588":{"x":-1260,"y":-130},"589":{"x":-1130,"y":-130},"591":{"x":-870,"y":-130},"593":{"x":-610,"y":-130},"594":{"x":-480,"y":-130},"595":{"x":-350,"y":-130},"600":{"x":300,"y":-130},"601":{"x":430,"y":-130},"602":{"x":560,"y":-130},"603":{"x":690,"y":-130},"604":{"x":820,"y":-130},"605":{"x":950,"y":-130},"607":{"x":1210,"y":-130},"608":{"x":1340,"y":-130},"609":{"x":1470,"y":-130},"610":{"x":1600,"y":-130},"611":{"x":1730,"y":-130},"612":{"x":1860,"y":-130},"620":{"x":2900,"y":-130},"628":{"x":-3470,"y":-260},"629":{"x":-3340,"y":-260},"630":{"x":-3210,"y":-260},"631":{"x":-3080,"y":-260},"632":{"x":-2950,"y":-260},"634":{"x":-2690,"y":-260},"635":{"x":-2560,"y":-260},"636":{"x":-2430,"y":-260},"637":{"x":-2300,"y":-260},"643":{"x":-1520,"y":-260},"644":{"x":-1390,"y":-260},"645":{"x":-1260,"y":-260},"649":{"x":-740,"y":-260},"651":{"x":-480,"y":-260},"652":{"x":-350,"y":-260},"653":{"x":-220,"y":-260},"657":{"x":300,"y":-260},"658":{"x":430,"y":-260},"659":{"x":560,"y":-260},"660":{"x":690,"y":-260},"661":{"x":820,"y":-260},"662":{"x":950,"y":-260},"664":{"x":1210,"y":-260},"665":{"x":1340,"y":-260},"666":{"x":1470,"y":-260},"667":{"x":1600,"y":-260},"668":{"x":1730,"y":-260},"669":{"x":1860,"y":-260},"673":{"x":2380,"y":-260},"674":{"x":2510,"y":-260},"675":{"x":2640,"y":-260},"676":{"x":2770,"y":-260},"677":{"x":2900,"y":-260},"678":{"x":3030,"y":-260},"679":{"x":3160,"y":-260},"680":{"x":3290,"y":-260},"681":{"x":3420,"y":-260},"685":{"x":-3470,"y":-390},"686":{"x":-3340,"y":-390},"688":{"x":-3080,"y":-390},"689":{"x":-2950,"y":-390},"690":{"x":-2820,"y":-390},"691":{"x":-2690,"y":-390},"692":{"x":-2560,"y":-390},"693":{"x":-2430,"y":-390},"696":{"x":-2040,"y":-390},"697":{"x":-1910,"y":-390},"701":{"x":-1390,"y":-390},"702":{"x":-1260,"y":-390},"703":{"x":-1130,"y":-390},"705":{"x":-870,"y":-390},"707":{"x":-610,"y":-390},"708":{"x":-480,"y":-390},"709":{"x":-350,"y":-390},"710":{"x":-220,"y":-390},"711":{"x":-90,"y":-390},"714":{"x":300,"y":-390},"715":{"x":430,"y":-390},"717":{"x":690,"y":-390},"718":{"x":820,"y":-390},"719":{"x":950,"y":-390},"721":{"x":1210,"y":-390},"722":{"x":1340,"y":-390},"724":{"x":1600,"y":-390},"725":{"x":1730,"y":-390},"726":{"x":1860,"y":-390},"730":{"x":2380,"y":-390},"731":{"x":2510,"y":-390},"732":{"x":2640,"y":-390},"733":{"x":2770,"y":-390},"734":{"x":2900,"y":-390},"735":{"x":3030,"y":-390},"736":{"x":3160,"y":-390},"737":{"x":3290,"y":-390},"738":{"x":3420,"y":-390},"745":{"x":-3080,"y":-520},"746":{"x":-2950,"y":-520},"749":{"x":-2560,"y":-520},"750":{"x":-2430,"y":-520},"753":{"x":-2040,"y":-520},"754":{"x":-1910,"y":-520},"757":{"x":-1520,"y":-520},"758":{"x":-1390,"y":-520},"759":{"x":-1260,"y":-520},"760":{"x":-1130,"y":-520},"761":{"x":-1000,"y":-520},"763":{"x":-740,"y":-520},"764":{"x":-610,"y":-520},"766":{"x":-350,"y":-520},"768":{"x":-90,"y":-520},"769":{"x":40,"y":-520},"771":{"x":300,"y":-520},"772":{"x":430,"y":-520},"775":{"x":820,"y":-520},"776":{"x":950,"y":-520},"778":{"x":1210,"y":-520},"779":{"x":1340,"y":-520},"782":{"x":1730,"y":-520},"783":{"x":1860,"y":-520},"787":{"x":2380,"y":-520},"788":{"x":2510,"y":-520},"794":{"x":3290,"y":-520},"795":{"x":3420,"y":-520},"802":{"x":-3080,"y":-650},"803":{"x":-2950,"y":-650},"806":{"x":-2560,"y":-650},"807":{"x":-2430,"y":-650},"808":{"x":-2300,"y":-650},"809":{"x":-2170,"y":-650},"810":{"x":-2040,"y":-650},"811":{"x":-1910,"y":-650},"813":{"x":-1650,"y":-650},"814":{"x":-1520,"y":-650},"815":{"x":-1390,"y":-650},"817":{"x":-1130,"y":-650},"819":{"x":-870,"y":-650},"820":{"x":-740,"y":-650},"823":{"x":-350,"y":-650},"826":{"x":40,"y":-650},"828":{"x":300,"y":-650},"829":{"x":430,"y":-650},"832":{"x":820,"y":-650},"833":{"x":950,"y":-650},"835":{"x":1210,"y":-650},"836":{"x":1340,"y":-650},"839":{"x":1730,"y":-650},"840":{"x":1860,"y":-650},"844":{"x":2380,"y":-650},"845":{"x":2510,"y":-650},"846":{"x":2640,"y":-650},"847":{"x":2770,"y":-650},"848":{"x":2900,"y":-650},"849":{"x":3030,"y":-650},"850":{"x":3160,"y":-650},"851":{"x":3290,"y":-650},"852":{"x":3420,"y":-650},"859":{"x":-3080,"y":-780},"860":{"x":-2950,"y":-780},"864":{"x":-2430,"y":-780},"865":{"x":-2300,"y":-780},"866":{"x":-2170,"y":-780},"867":{"x":-2040,"y":-780},"871":{"x":-1520,"y":-780},"878":{"x":-610,"y":-780},"879":{"x":-480,"y":-780},"880":{"x":-350,"y":-780},"885":{"x":300,"y":-780},"886":{"x":430,"y":-780},"888":{"x":690,"y":-780},"889":{"x":820,"y":-780},"890":{"x":950,"y":-780},"892":{"x":1210,"y":-780},"893":{"x":1340,"y":-780},"895":{"x":1600,"y":-780},"896":{"x":1730,"y":-780},"897":{"x":1860,"y":-780},"901":{"x":2380,"y":-780},"902":{"x":2510,"y":-780},"908":{"x":3290,"y":-780},"909":{"x":3420,"y":-780}}';

	var signList = {
		status: false,
		queue: []
	};
	var _index = [];
	// 生成虚拟数据 基本版   410个头像
	for(var i = 0; i < 410; i++) {
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
		table[i].p_x = i % 29 + 1;
		table[i].p_y = Math.floor(i / 29) + 1;
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

	}

	function showSignIn() {
		if(signList.queue.length <= 0) {
			signList.status = false;
			return;
		} else {
			signList.status = true;
		}

		var user = signList.queue.pop();

		if(_index.length == 0) {
			for(var i = 0; i < 410; i++) {
				_index.push(i)
			}
			_index.sort(function() {
				return 0.5 - Math.random()
			})
		}

		//一次存入1个头像
		for(var j = 0; j < 1; j++) {
			if(_index.length == 0) {
				for(var i = 0; i < 410; i++) {
					_index.push(i)
				}
				_index.sort(function() {
					return 0.5 - Math.random()
				})
			}
			var index = _index.pop();
			$('.new-card img')[index].src = user.headimg;
		}

		$('#newbigcard').find('img').attr('src', user.headimg)
		$('#newbigcard').find('span').html(user.username + '</br>' + user.wish);
		$('#newbigcard').addClass('signani');

		setTimeout(function() {
			$('#newbigcard').removeClass('signani');
			setTimeout(showSignIn, 1000);
		}, 4000)

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
			object.position.x = (table[i].p_x * 180) - 2730;
			object.position.y = -(table[i].p_y * 180) + 1300;
			targets.table.push(object);
		}

		targets.table[targets.table.length - 4].position.x = 10000;
		targets.table[targets.table.length - 4].position.y = 10000;
		targets.table[targets.table.length - 3].position.x = 10000;
		targets.table[targets.table.length - 3].position.y = 10000;
		targets.table[targets.table.length - 2].position.x = 10000;
		targets.table[targets.table.length - 2].position.y = 10000;
		targets.table[targets.table.length - 1].position.x = 10000;
		targets.table[targets.table.length - 1].position.y = 10000;

		// sphere
		var vector = new THREE.Vector3();
		var spherical = new THREE.Spherical();

		for(var i = 0, l = objects.length; i < l; i++) {

			var phi = Math.acos(-1 + (2 * i) / l);
			var theta = Math.sqrt(l * Math.PI) * phi;

			var object = new THREE.Object3D();

			spherical.set(1800, phi, theta);

			object.position.setFromSpherical(spherical);

			vector.copy(object.position).multiplyScalar(2);

			object.lookAt(vector);

			targets.sphere.push(object);
		}

		var vector = new THREE.Vector3();
		var cylindrical = new THREE.Cylindrical();

		for(var i = 0, l = objects.length; i < l; i++) {
			//var theta = i * 0.175 + Math.PI;
			var theta = i * 0.175 + Math.PI;
			//var y = -(i * 5) + 450;
			var y = -(i * 10) + 2050;
			var object = new THREE.Object3D();
			// 参数一 圈的大小900  参数二 左右间距 参数三 上下间距
			cylindrical.set(1800, theta, y);
			object.position.setFromCylindrical(cylindrical);
			vector.x = object.position.x * 2;
			vector.y = object.position.y;
			vector.z = object.position.z * 2;
			object.lookAt(vector);
			targets.helix.push(object);
		}

		// grid
		for(var i = 0; i < objects.length; i++) {
			var object = new THREE.Object3D();
			object.position.x = ((i % 5) * 500) - 900; // 400 图片的左右间距  800 x轴中心店
			object.position.y = (-(Math.floor(i / 5) % 5) * 500) + 1000; // 500 y轴中心店
			object.position.z = (Math.floor(i / 50)) * 200 - 600; // 300调整 片间距 800z轴中心店
			targets.grid.push(object);
		}

		var txtP = [];

		var t = JSON.parse(logoPostion);

		for(var i in t) {
			var o = {};
			o.x = t[i].x;
			o.y = t[i].y;
			txtP.push(o);
		}
//		console.log(txtP);
		console.log(txtP.length);

		for(var i = 0; i < objects.length; i++) {
			var object = new THREE.Object3D();
			object.position.x = txtP[i].x;
			object.position.y = txtP[i].y;
			object.position.z = 0;
			targets.logo.push(object);
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
					transform(targets.logo, 1000);
					break;
				case 1:
					transform(targets.table, 1000);
					break;
				case 2:
					transform(targets.logo, 1000);
					//transform(targets.helix, 1000);
					break;
				case 3:
					transform(targets.grid, 1000);
					break;
				case 4:
					transform(targets.logo, 1000);
					break;
				case 5:
					transform(targets.sphere, 1000);
					break;
				case 6:
					transform(targets.logo, 1000);
					break;
				case 7:
					transform(targets.helix, 1000);
					break;
				case 8:
					transform(targets.logo, 1000);
					break;
				case 9:
					transform(targets.grid, 1000);
					break;
				default:
					break;
			}

			ini = ini >= 9 ? 0 : ini + 1;

		}, 15000);

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
		scene.rotation.y += 0.01;

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
			for(var i = 0; i < 410; i++) {
				_index.push(i)
			}
			_index.sort(function() {
				return 0.5 - Math.random()
			})
		}

		for(var i = 0; i < obj.length; i++) {
			for(var j = 0; j < 1; j++) {
				if(_index.length == 0) {
					for(var i = 0; i < 410; i++) {
						_index.push(i)
					}
					_index.sort(function() {
						return 0.5 - Math.random()
					})
				}
				var index = _index.pop();
				$('.new-card img')[index].src = obj[i].reheadimgurl;
			}
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