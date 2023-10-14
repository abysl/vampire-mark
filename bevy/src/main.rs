use bevy::diagnostic::{FrameTimeDiagnosticsPlugin, LogDiagnosticsPlugin};
use bevy::prelude::*;
use bevy::prelude::Res;
use bevy::window::{PresentMode, WindowTheme};
use bevy_pixel_camera::{
    PixelCameraBundle, PixelCameraPlugin
};
use rand::Rng;

fn main() {
    App::new()
        .add_systems(Startup, setup)
        .add_systems(Startup, setup_entity_spawner)
        .add_systems(Update, move_character)
        .add_systems(Update, move_entities)
        .add_systems(Update, spawn_entities)
        .add_systems(Update, print_entity_count)
        .add_plugins(DefaultPlugins.set(ImagePlugin::default_nearest()).set(
            WindowPlugin {
                primary_window: Some(Window {
                    title: "I am a window!".into(),
                    resolution: (1920., 1080.).into(),
                    present_mode: PresentMode::Immediate,
                    fit_canvas_to_parent: true,
                    prevent_default_event_handling: false,
                    window_theme: Some(WindowTheme::Dark),
                    ..default()
                }),
                ..default()
            }),
        )
        .add_plugins(PixelCameraPlugin)
        .add_plugins(LogDiagnosticsPlugin::default())
        .add_plugins(FrameTimeDiagnosticsPlugin::default())
        .run();
}


fn setup_entity_spawner(mut commands: Commands) {
    commands.insert_resource(EntitySpawner::default());
    commands.insert_resource(PrintTimer::default());  // Add this line
}

#[derive(Resource)]
struct PrintTimer {
    timer: Timer,
}

impl Default for PrintTimer {
    fn default() -> Self {
        Self {
            timer: Timer::from_seconds(1.0, TimerMode::Repeating),
        }
    }
}

#[derive(Component)]
struct Character;

#[derive(Component)]
struct Movement {
    direction: Vec3,
}

fn setup(
    mut commands: Commands,
    asset_server: Res<AssetServer>,
) {
    commands.spawn(PixelCameraBundle::from_resolution(320, 180, true));

    let texture_handle = asset_server.load("archer.png");

    for _ in 0..10 {
        let direction = Vec3::new(
            rand::thread_rng().gen_range(-1.0..1.0),
            rand::thread_rng().gen_range(-1.0..1.0),
            0.0,
        ).normalize_or_zero();
        commands
            .spawn(SpriteBundle {
                texture: texture_handle.clone(),
                transform: Transform {
                    scale: Vec3::new(1.0 , 1.0 , 0.0),
                    ..Default::default()
                },
                ..Default::default()
            })
            .insert(Character)
            .insert(Movement { direction });
    }
}

fn move_entities(
    time: Res<Time>,
    mut query: Query<(&mut Transform, &mut Movement)>,
) {
    let speed = 32.0;

    for (mut transform, mut movement) in query.iter_mut() {
        let delta = movement.direction * speed * time.delta_seconds();
        transform.translation += delta;

        if transform.translation.x <= -160.0 || transform.translation.x >= 160.0 {
            movement.direction.x = -movement.direction.x;
        }
        if transform.translation.y <= -90.0 || transform.translation.y >= 90.0  {
            movement.direction.y = -movement.direction.y;
        }
    }
}

#[derive(Resource)]
struct EntitySpawner {
    timer: Timer,
}

impl Default for EntitySpawner {
    fn default() -> Self {
        Self {
            timer: Timer::from_seconds(0.01, TimerMode::Repeating),
        }
    }
}

fn spawn_entities(
    mut commands: Commands,
    asset_server: Res<AssetServer>,
    time: Res<Time>,
    mut spawner: ResMut<EntitySpawner>,
) {
    if spawner.timer.tick(time.delta()).just_finished() {
        let texture_handle = asset_server.load("archer.png");
        let direction = Vec3::new(
            rand::thread_rng().gen_range(-1.0..1.0),
            rand::thread_rng().gen_range(-1.0..1.0),
            0.0,
        ).normalize_or_zero();
        commands
            .spawn(SpriteBundle {
                texture: texture_handle.clone(),
                transform: Transform {
                    scale: Vec3::new(1.0 , 1.0 , 0.0),
                    ..Default::default()
                },
                ..Default::default()
            })
            .insert(Character)
            .insert(Movement { direction });
    }
}

fn print_entity_count(
    time: Res<Time>,
    mut print_timer: ResMut<PrintTimer>,
    query: Query<&Character>,
) {
    if print_timer.timer.tick(time.delta()).just_finished() {
        println!("Entity count: {}", query.iter().count());
    }
}

#[derive(Component)]
struct Player;

fn move_character(
    time: Res<Time>,
    keyboard_input: Res<Input<KeyCode>>,
    mut query: Query<&mut Transform, With<Player>>,
) {
    let mut direction = Vec3::ZERO;

    if keyboard_input.pressed(KeyCode::W) {
        direction += Vec3::new(0., 1., 0.);
    }

    if keyboard_input.pressed(KeyCode::A) {
        direction += Vec3::new(-1., 0., 0.);
    }

    if keyboard_input.pressed(KeyCode::S) {
        direction += Vec3::new(0., -1., 0.);
    }

    if keyboard_input.pressed(KeyCode::D) {
        direction += Vec3::new(1., 0., 0.);
    }

    if direction != Vec3::ZERO {
        let speed = 32.0;
        let delta = direction.normalize() * speed * time.delta_seconds();

        for mut transform in query.iter_mut() {
            transform.translation += delta;
        }
    }
}

